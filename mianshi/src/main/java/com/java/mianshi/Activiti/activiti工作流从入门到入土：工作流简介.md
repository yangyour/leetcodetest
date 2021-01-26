## 一、activiti介绍
Activiti是由Alfresco软件在2020年5月17日发布的业务流程管理(BMP)框架，它是覆盖了业务流程
管理、工作流、服务协作等领域的一个开源的、灵活的、易扩展的可执行流程语言框架。Acitivi基于
Apache许可的开源BPM平台，创始人Tom Baeyens是JBoss jBPM的项目架构师，它特色是提供了
eclipse插件，开发人员可以通过插件直接绘画出业务流程图。
* 官网：[http://www.activiti.org/](http://www.activiti.org/)
* 下载:[http://www.activiti.org/download.html](http://www.activiti.org/download.html)

目前，activiti中的5.22版本是用的最多的一个版本，本次教程也是使用这个版本进行讲解，最新版升到
了7.0版本。

在开始教程前，我们需要先明确下面的一些概念和知识，可以帮助我们更好的理解工作流的开发。

## 二、工作流引擎
ProcessEngine对象，这是Activiti工作的核心。负责生成流程运行的各种实例及数据、监控和管理
流程的运行。

用百度更为通俗的话来说就是：就是一辆汽车的发动机，就好比一辆汽车，外表做的再漂亮，如果发动机
有问题就只是一个摆设。应用系统的弹性就好比引擎转速方面的性能，加速到100公里需要1个小时(业务
流程发生变动需要进行半年的程序修改)还能叫好车吗？引擎动不动就熄火(程序因为逻辑的问题而陷入
死循环)的车还敢开吗？

## 三、 BPMN
在Activiti工作流中用到了一个BPMN的文件，主要是用来描述业务流程的基本的符号，利用各个组件
能够组成一个业务流程图，整个业务也是根据这个图来走的，其实用xml格式打开，就是一个xml文件。

下面就是bpmn组件的示意图
![](https://upload-images.jianshu.io/upload_images/5824016-ae46d7fccac467f0.png?imageMogr2/auto-orient/strip|imageView2/2/w/279/format/webp)
## 四、 数据库
在我们进行业务流程开发的时候，是会产生很多的数据的，那么这些数据都是放在哪里的呢？

是的，其实就是存放在数据库的。

在Activiti工作流的后台是有数据库支持的，所有的表都以Act_开头,利用这些数据的表，就能把整个
业务流程的数据保存下来，然后利用这些数据进行不同业务的开发。

数据库示意图：
![](https://upload-images.jianshu.io/upload_images/5824016-4deba5ad60c2434e.png?imageMogr2/auto-orient/strip|imageView2/2/w/153/format/webp)

下面对这些表做一些基本的解释
* 资源库流程规则表
  * act_re_deployment部署信息表
  * act_re_model 流程设计模型部署表
  * act_re_procdef 流程定义数据表
* 运行时数据库表
  * act_ru_execution 运行时流程执行实例表
  * act_ru_identitylink 运行时流程人员表，主要储存任务节点与参与者的相关信息
  * act_ru_task 运行时任务节点表
  * act_ru_variable 运行时流程变量数据库表
* 历史数据库表
  * act_hi_actinst 历史节点表
  * act_hi_attachment 历史附件表
  * act_hi_comment 历史意见表
  * act_hi_identitylink 历史流程人员表
  * act_hi_detail 历史详情表，提供历史变量的查询
  * act_hi_procinst 历史流程实例表
  * act_hi_taskinst 历史任务实例表
  * act_hi_varinst 历史变量表
* 组织机构表
  * act_id_group 用户组信息表
  * act_id_info 用户扩展信息表
  * act_id_membership 用户与用户组对应信息表
  * act_id_user 用户信息表

这些表用的很少，因为我们一般会自己做一个权限管理，所以不会用activiti自身所带的表
* 通用数据表
  * act_ge_bytearray 二进制数据表
  * act_ge_propetty 属性数据库表存储整个流程引擎级别的数据，初始化表结构时，会默认插入
  三条记录。
  
这两张表的数据是不能够随意删除的，删除可能会出现问题。

