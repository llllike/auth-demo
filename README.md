## 快速使用

## 1、antiSamy

antiSamy 是一个防止XSS跨域攻击的技术

用于过滤掉客户端发送包含的脚本文件

来保障Web的正常运行

先引入antiSamy 模块的jar包

再在启动类上添加@EnableAntiSamy注解

## 2、cache

cache模块集成了redis和mybatis-plus，把redis作为mybatis-plus的二级缓存

引入依赖后，只需要在启动类上添加@EnableRedisCache注解

## 3、dozer

dozer是用于对象的自动转换，引入依赖后即可使用

## 4、JWT

JWT是用于Web前后端分离开发，token认证的一项技术

先引入依赖，再在启动类上添加@EnableJWT注解

## 5、knife4j

knife4j也是用于前后端分离开发，接口文档。

支持对接口添加注释，提供接口的参数，返回值，以及测试

## 6、logback

logback是日志技术，引入依赖后，再在启动类上添加@EnableLogback注解

## 7、matrixVariable

matrixVariable添加后支持get请求路径中添加矩阵变量

## 8、MPInterceptor

MPInterceptor添加mybatis-plus拦截器，默认添加分页拦截器和乐观锁拦截器

## 9、RedisTemplate

对RedisTemplate做一个序列化配置

## 10、trans

trans邮件发送的自动配置

## 11、validator

validator对传入参数的自动校验

阿里云OSS: https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/code/auth-demo.zip

Github: https://github.com/llllike/auth-demo

Gitee: https://gitee.com/jk_2_yu/auth-demo