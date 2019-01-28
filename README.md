# ImHere

这将会是一个人们即时分享心中所想的App，它会以一个坐标改变一些人。

开工加油！

##关于
IM部分的界面学习了
https://github.com/xiangyunwan/imdemo-master
的作品，实际使用里对相当一部分做了简化，非常感谢提供思路

记录一些遇到的问题：
1、虚拟机无法正常使用百度定位，有时能用但大部分情况在获得定位权限的情况下报167错误：
    http://lbsyun.baidu.com/index.php?title=android-locsdk/qa，见第13条的第9条

2、在集成定位服务至全局的时候发生的一些问题，见 AndroidManifests.xml

3、Lottie 太坑爹了，只支持 scaleType 的 centerCrop 和 centerInside，不支持 center 。虚拟机上不出错，真机上 View 都不知道飞哪里去了。坑爹啊。
