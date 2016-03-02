# 对比blog_admin
- Controller method只对应一个url
- 增加线程安全token机制的服务器端防止重复提交功能


# 求解惑
## 疑问1
一直纠结于service是用Map还是Bean，苦苦纠结很久，最终还是使用了Map。
其缺点在于泛化了对象，弱化了面向对象的思想。强制的类型转换，隐藏的运行时错误，维护的不便，数据隐性。在反序列化为Map时部分期望是Long的数据转化为了Integer
优点是开发灵活。
Bean的好处就是维护方便，显式的数据，方便的维护，强的面向对象思想。缺点是开发麻烦，项目可能会写上大量的Bean。
## 疑问2
关于缓存：第一次使用缓存，关于缓存的一致性，在大型项目中是如何控制的？通过注解，要考虑关联缓存空间的清空（降低命中率），或者说清空部分？这需要人工判断，是否可以做到自动化？拦截SQL解析关联表做清空？
# BUGS
(1)Shiro的RememberMe无效：http://blog.csdn.net/nsrainbow/article/details/36945267
(2)对于角色的[用户授权-是否可管理]，前端没得选择，默认为不可管理，本应价格checkbox来控制的。0.0# 前端太渣，那个交互写不出来。
(3)登陆失效后，应该给出弹出框让他登陆
(4)缓存的key生成器没有考虑方法参数为null的情况
