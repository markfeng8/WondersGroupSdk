# 湖州医后付 SDK 项目

#### 介绍

湖州医后付项目是将「医后付」功能模块封装成一个 SDK 提供给健康湖州 APP 进行集成使用。

作为 SDK 提供方，应该做到将所有功能封装到一个库中，方便集成方集成简单，最好是一两行代码就能方便集成和调用，因此将功能全部封
装到一个 library 中，然后打成 aar 包提供给调用方使用，后来这种方式也比较麻烦，索性后面就将代码发布到 bintray 远程依赖仓
库中，这样不仅提高了我们自己打包上传的效率也方便了集成方的更新和修改操作。

#### 软件架构

项目采用 MVP 架构，按照页面功能进行划分，每一个 Activity 页面（View）都对应着一个 Presenter 和 Model 层，为了方便接
口管理，将三层的接口都放到一个契约类中（Contract）方便管理。

#### 发布到 bintray 步骤

1. 修改版本号：

例如：

```
def releaseVersion = '0.1.11'
```

2. 打开 Terminal 执行上传命令：

```
./gradlew :library:uploadBintray
```

温馨提示：如果是 Windows 系统执行下面的命令：

```
Windows 执行如下命令：
gradlew clean build bintrayUpload -PbintrayUser=wondersgroupandroid863 -PbintrayKey=e4ba8b931617cb82f6ec1459cd26163a1de695b3 -PdryRun=false
```

3. 测试最新依赖

把下面的 lastest_version 替换为最新的版本号，在 app module 下进行同步并测试。

```
implementation 'com.wondersgroup.android:WondersGroupSdk:<lastest_version>'
```

#### 将 example 发布到蒲公英

1. 打开 app 的 build.gradle 文件，修改 appVersionCode、appVersionName、getUpdateDescription；
2. 打开 Terminal 执行如下命令：

```
./gradlew :app:uploadApk
```

因为 uploadApk task 是依赖于 assembleRelease task 的，因此执行完打包命令后会执行上传命令的。

#### 关于集成文档编写

项目更目录的 markdown 目录下已经编写了一份集成文档，每次发布版本就在此版本基础上进行修改即可。

#### 参与贡献者

1. x-sir(辛鹏飞)

#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)