# 介绍

本仓库包括橘浪家政系统后端 小程序端 管理端

# 软件架构

管理端+后端采用hilla框架 数据库采用mysql

# 安装教程

需要先安装node mysql8.3 maven（若IDE为IntellJ有编辑器自带的maven）

# 使用说明

1. 默认连接到本地3306端口的mysq数据库

2. 启动要先在环境变量中设置好DEV_MYSQL_USERNAME 、DEV_MYSQL_PASSWORD，详见src/main/resources/application-dev.yml；并调用src/init.sql建库，（jpa会自动建表）

3. 另外由于还没做注册功能，要现在user库中插入用户名和密码才能登录

# 参与贡献

1. 查看中[橘浪家政系统 (qq.com)](https://docs.qq.com/sheet/DQlFVR1haaEVZZW1E?tab=r14pmh)的进展sheet了解进展情况，确定目标
2. 新建 dev_by_xxx 分支
3. 提交代码
4. 新建 Pull Request到dev_backend

# 关于 Hilla 工程

该项目是用 Spring Boot 创建您的 Hilla 应用程序。

## Running the application

运行 Hilla 应用程序
运行应用程序有两种方法：您可以使用 IDE 来运行Application.java - 推荐这样做，因为它支持调试；或者您可以从命令行运行默认的
Maven 目标（即mvn）。

Hilla 会在保存时自动重新加载对前端和后端代码的更改。IntelliJ 要求您在更改 Java 代码后手动构建项目，而 VS Code 将在保存时自动构建项目。

## 部署到生产环境

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/myapp-1.0-SNAPSHOT.jar` (NOTE, replace
`myapp-1.0-SNAPSHOT.jar` with the name of your jar).

## 工程目录结构

<table style="width:100%; text-align: left;">
  <tr><th>目录</th><th>描述</th></tr>
  <tr><td><code>frontend/</code></td><td>Client-side source directory</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>index.html</code></td><td>HTML template</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>index.ts</code></td><td>Frontend 
entrypoint, bootstraps a React application</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>routes.tsx</code></td><td>React Router routes definition</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>MainLayout.tsx</code></td><td>Main 
layout component, contains the navigation menu, uses <a href="https://hilla.dev/docs/react/components/app-layout">
App Layout</a></td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>views/</code></td><td>UI view 
components</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>themes/</code></td><td>Custom  
CSS styles</td></tr>
  <tr><td><code>src/main/java/&lt;groupId&gt;/</code></td><td>Server-side 
source directory, contains the server-side Java views</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>Application.java</code></td><td>Server entry-point</td></tr>
</table>
