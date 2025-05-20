#### Springboot 入门项目

### 项目概述
本项目为一个 Spring Boot 入门项目，包含基础的 RESTful API 和测试框架。项目主要使用 Java 和 Kotlin 语言，并结合使用 JUnit 进行单元测试，利用 JMH 和 K6 进行性能测试。

### 环境准备
1. 克隆项目：
    ```bash
    git clone <项目的Git仓库地址>
    ```
2. 进入项目目录：
    ```bash
    cd spring-mock-app
    ```
3. 安装依赖：
    ```bash
    ./gradlew clean build
    ```

### 运行项目
使用以下命令启动 Spring Boot 应用：
```bash
./gradlew bootRun
```

### 测试指南
- **单元测试**：执行以下命令运行所有单元测试：
  ```bash
  ./gradlew test
  ```
- **性能测试**：使用 K6 运行性能测试，确保已安装 K6 工具：
  ```bash
  k6 run stressTest.js
  ```

### API 文档
本项目提供如下 API：
- `GET /api/products`：获取所有产品
- `GET /api/products/{id}`：根据 ID 获取特定产品

### 贡献指南
欢迎贡献出色的方案！请遵循以下步骤：
1. Fork 仓库
2. 新建特性分支 (feature/your-feature)
3. 提交您的更改 (git commit -am 'Add your feature')
4. 推送到分支 (git push origin feature/your-feature)
5. 提交一个 Pull Request
