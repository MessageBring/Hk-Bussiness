# Hk-Bussiness项目结构说明
### hk-common:
    公共服务模块，封装工具类，枚举和其它的全项目可共用的类，hk-service依赖该模块
### hk-dao:
    数据访问模块，封装对数据库的访问操作，hk-service依赖该模块
### hk-model:
    数据模型模块，封装po，bo,dto,vo等数据类,hk-dao依赖该模块
### hk-service
    业务模块，处理业务逻辑，hk-web依赖该模块
### hk-third
    第三方服务模块，对接的第三方服务，对如第三方支付，发送短信，邮件等功能封装，hk-service依赖该模块
### hk-web
    控制器模块，封装controller，整个项目的入口

# 项目说明
    就是个搭着玩的东西，后面想到什么再加吧    