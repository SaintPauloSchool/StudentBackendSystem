# 學生手冊管理系統 - 前後端分離部署指南

## 系統架構
- **前端**: Vue 3 + Element Plus + Vite
- **後端**: Spring Boot + Spring Security + MyBatis Plus
- **部署方式**: 前後端完全分離部署

## 部署環境要求

### 後端環境
- JDK 8 或更高版本
- Tomcat 8.5 或更高版本
- MySQL 5.7 或更高版本

### 前端環境
- Node.js 14 或更高版本
- Nginx 或 Apache HTTP Server（用於靜態文件服務）

## 部署步驟

### 1. 後端部署

#### 1.1 數據庫準備
```sql
-- 創建數據庫
CREATE DATABASE student_handbook DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 執行SQL腳本
USE student_handbook;
SOURCE sql/student_handbook.sql;
```

#### 1.2 修改配置文件
編輯 `sp-api/src/main/resources/application-druid.yml`:
```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://你的數據庫地址:3306/student_handbook?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: 你的用戶名
        password: 你的密碼
```

#### 1.3 打包WAR文件
在項目根目錄執行：
```bash
mvn clean package -DskipTests
```

成功後會在 `sp-api/target/` 目錄生成 `sp-api.war` 文件。

#### 1.4 部署到Tomcat
1. 將生成的 `sp-api.war` 文件複製到 Tomcat 的 `webapps` 目錄
2. 啟動 Tomcat 服務器
3. 訪問地址: `http://localhost:8080/sp-api`

### 2. 前端部署

#### 2.1 構建前端項目
進入前端目錄並執行：
```bash
cd student-backend-system-vue
npm install
npm run build
```

成功後會在 `dist` 目錄生成構建文件。

#### 2.2 部署靜態文件
將 `student-backend-system-vue/dist` 目錄中的所有文件複製到Web服務器的網站根目錄。

#### 2.3 Nginx配置示例
```nginx
server {
    listen 80;
    server_name your-domain.com;
    
    # 前端靜態文件
    location / {
        root /path/to/dist;
        index index.html;
        try_files $uri $uri/ /index.html;
    }
    
    # API代理
    location /api/ {
        proxy_pass http://localhost:8080/sp-api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

## API接口說明

### 學生手冊管理接口
- `GET /system/handbook/list` - 獲取手冊列表
- `GET /system/handbook/{id}` - 獲取手冊詳情
- `POST /system/handbook` - 新增手冊
- `PUT /system/handbook/{id}` - 更新手冊
- `DELETE /system/handbook/{id}` - 刪除手冊
- `DELETE /system/handbook/batch` - 批量刪除手冊

## 注意事項

1. **跨域配置**: 後端已配置CORS支持，前端可以直接調用API
2. **安全性**: 生產環境請修改默認密碼和JWT密鑰
3. **性能優化**: 建議配置Redis緩存和數據庫連接池
4. **日誌監控**: 檢查Tomcat和應用日誌確保系統正常運行

## 故障排除

### 常見問題
1. **API調用失敗**: 檢查後端服務是否正常啟動，確認端口和路徑配置
2. **數據庫連接失敗**: 檢查數據庫配置和網絡連接
3. **靜態資源加載失敗**: 檢查前端構建文件和Web服務器配置
4. **權限驗證失敗**: 確認JWT配置和用戶認證信息

### 日誌查看
- 後端日誌: Tomcat/logs 目錄下的 catalina.out 和應用日誌
- 前端日誌: 瀏覽器開發者工具 Console 面板

## 版本信息
- 當前版本: 1.0.0
- 最後更新: 2024年