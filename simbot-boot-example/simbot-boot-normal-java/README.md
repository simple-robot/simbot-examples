# boot normal example

基于simbot-boot模块的基础示例项目，使用 mirai组件(qq bot) 作为示例组件.

## Bot 配置
在boot模块中，所有bot的配置都放在资源目录下的 `resources/simbot-bots/**.bot` 文件中。

在simbot3.x中的 `.bot` 文件等同于 `json` 格式的文件，因此如果你使用的IDE是idea，
你可以通过右键文件，选择 `Revert File Type Override`，选择 `Json` 来使用idea提供的文件高亮。

如果你使用其他的IDE，请自行查询是否有类型的功能。


## 依赖注入
simbot3.x中，boot模块保留并**简化**了依赖注入功能。现在boot模块中**不再支持**配置文件注入了。

对于依赖管理，将一个普通的类注入依赖中，使用 `@love.forte.di.annotation.Beans` 注解：

```java
import love.forte.di.annotation.Beans;

@Beans
public class Example {
    // ...
}
```

需要进行计算而注入的类，则使用 ``



## 监听函数
simbot3.x中，所有的"监听函数"