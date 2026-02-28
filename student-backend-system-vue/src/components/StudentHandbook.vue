<template>
  <div class="student-handbook">
    <h2>学生手册</h2>
    
    <!-- 搜索表单 -->
    <el-form :inline="true" :model="searchForm" class="search-form">
      <el-form-item label="科目及老师">
        <el-input v-model="searchForm.subjectTeacher" placeholder="科目及老师"></el-input>
      </el-form-item>
      <el-form-item label="类别">
        <el-input v-model="searchForm.category" placeholder="类别"></el-input>
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="searchForm.title" placeholder="标题"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchHandbooks">
          <el-icon><Search /></el-icon>
          <span>搜索</span>
        </el-button>
        <el-button @click="resetForm">
          <el-icon><Refresh /></el-icon>
          <span>重置</span>
        </el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <div class="toolbar">
      <el-button type="primary" @click="showAddDialog">
        <el-icon><Plus /></el-icon>
        <span>新增</span>
      </el-button>
      <el-button type="success" :disabled="!selectedRows.length" @click="showEditDialog">
        <el-icon><Edit /></el-icon>
        <span>修改</span>
      </el-button>
      <el-button type="danger" :disabled="!selectedRows.length" @click="deleteHandbooks">
        <el-icon><Delete /></el-icon>
        <span>删除</span>
      </el-button>
    </div>

    <!-- 数据表格 -->
    <el-table 
      :data="handbookList" 
      style="width: 100%" 
      @selection-change="handleSelectionChange"
      v-loading="loading">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="subjectTeacher" label="科目及老师" width="180"></el-table-column>
      <el-table-column prop="category" label="类别" width="120"></el-table-column>
      <el-table-column prop="title" label="标题" width="180"></el-table-column>
      <el-table-column label="时间" width="200">
        <template #default="scope">
          {{ scope.row.startTime }}-{{ scope.row.endTime }}
        </template>
      </el-table-column>
      <el-table-column prop="content" label="内容"></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button size="small" @click="showEditDialog(scope.row)">
            <el-icon><Edit /></el-icon>
            <span>编辑</span>
          </el-button>
          <el-button size="small" type="danger" @click="deleteHandbook(scope.row)">
            <el-icon><Delete /></el-icon>
            <span>删除</span>
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 20, 50]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>

    <!-- 新增/编辑对话框 -->
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="50%">
      <el-form :model="currentHandbook" label-width="100px">
        <el-form-item label="科目及老师">
          <el-input v-model="currentHandbook.subjectTeacher"></el-input>
        </el-form-item>
        <el-form-item label="类别">
          <el-input v-model="currentHandbook.category"></el-input>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="currentHandbook.title"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker
            v-model="currentHandbook.startTime"
            type="date"
            placeholder="选择开始时间"
            format="MM/dd/yyyy"
            value-format="MM/dd/yyyy">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
            v-model="currentHandbook.endTime"
            type="date"
            placeholder="选择结束时间"
            format="MM/dd/yyyy"
            value-format="MM/dd/yyyy">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="currentHandbook.content"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">
            <el-icon><Close /></el-icon>
            <span>取 消</span>
          </el-button>
          <el-button type="primary" @click="saveHandbook">
            <el-icon><Check /></el-icon>
            <span>确 定</span>
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
import { 
  Search,
  Refresh,
  Plus,
  Edit,
  Delete,
  Close,
  Check
} from '@element-plus/icons-vue'

export default {
  name: 'StudentHandbook',
  components: {
    Search,
    Refresh,
    Plus,
    Edit,
    Delete,
    Close,
    Check
  },
  data() {
    return {
      loading: false,
      handbookList: [],
      selectedRows: [],
      searchForm: {
        subjectTeacher: '',
        category: '',
        title: ''
      },
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      isEdit: false,
      currentHandbook: {
        id: null,
        subjectTeacher: '',
        category: '',
        title: '',
        startTime: '',
        endTime: '',
        content: ''
      }
    }
  },
  mounted() {
    this.fetchHandbookList()
  },
  methods: {
    // 获取学生手册列表
    async fetchHandbookList() {
      this.loading = true
      try {
        const response = await axios.get('/api/system/handbook/list', {
          params: {
            ...this.searchForm,
            pageNum: this.currentPage,
            pageSize: this.pageSize
          }
        })
        this.handbookList = response.data.rows
        this.total = response.data.total
      } catch (error) {
        console.error('获取学生手册列表失败:', error)
        this.$message.error('获取数据失败')
      } finally {
        this.loading = false
      }
    },

    // 搜索
    searchHandbooks() {
      this.currentPage = 1
      this.fetchHandbookList()
    },

    // 重置表单
    resetForm() {
      this.searchForm = {
        subjectTeacher: '',
        category: '',
        title: ''
      }
      this.currentPage = 1
      this.fetchHandbookList()
    },

    // 处理选择变化
    handleSelectionChange(selection) {
      this.selectedRows = selection
    },

    // 处理分页大小变化
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.fetchHandbookList()
    },

    // 处理当前页变化
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchHandbookList()
    },

    // 显示新增对话框
    showAddDialog() {
      this.dialogTitle = '新增学生手册'
      this.isEdit = false
      this.currentHandbook = {
        id: null,
        subjectTeacher: '',
        category: '',
        title: '',
        startTime: '',
        endTime: '',
        content: ''
      }
      this.dialogVisible = true
    },

    // 显示编辑对话框
    showEditDialog(row) {
      this.dialogTitle = '编辑学生手册'
      this.isEdit = true
      this.currentHandbook = { ...row }
      this.dialogVisible = true
    },

    // 保存学生手册
    async saveHandbook() {
      try {
        if (this.isEdit) {
          // 编辑
          await axios.put(`/api/system/handbook/${this.currentHandbook.id}`, this.currentHandbook)
          this.$message.success('更新成功')
        } else {
          // 新增
          await axios.post('/api/system/handbook', this.currentHandbook)
          this.$message.success('新增成功')
        }
        this.dialogVisible = false
        this.fetchHandbookList()
      } catch (error) {
        console.error('保存失败:', error)
        this.$message.error('保存失败')
      }
    },

    // 删除单个学生手册
    async deleteHandbook(row) {
      try {
        await this.$confirm('确认删除该条记录吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await axios.delete(`/api/system/handbook/${row.id}`)
        this.$message.success('删除成功')
        this.fetchHandbookList()
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除失败:', error)
          this.$message.error('删除失败')
        }
      }
    },

    // 批量删除学生手册
    async deleteHandbooks() {
      try {
        await this.$confirm(`确认删除选中的${this.selectedRows.length}条记录吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        const ids = this.selectedRows.map(row => row.id)
        await axios.delete('/api/system/handbook/batch', { data: ids })
        this.$message.success('删除成功')
        this.fetchHandbookList()
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除失败:', error)
          this.$message.error('删除失败')
        }
      }
    }
  }
}
</script>

<style scoped>
.student-handbook {
  padding: 20px;
}

.search-form {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.toolbar {
  margin-bottom: 20px;
}

.el-pagination {
  margin-top: 20px;
  text-align: right;
}
</style>