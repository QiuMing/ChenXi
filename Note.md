#关于MVP 的思考

V(view层, Activity,Fragment;还有一些视图层接口及其实现类:受业务逻辑影响的视图部分被抽象定义层接口，交给Presenter)
^
|
P(Presenter:主持人，作为中间层，持有View和Model的引用，完成View于Model间的交互)
^
|
M(业务层:bean;业务逻辑;还有一些业务层接口及其实现:和视图层有交互的，会被抽象定为接口，交给Presenter)


以上,我们会多写一些代码：接口及其实现类，但实现了M 和 V 之间的解耦。


#FastAndroid 中的MVP
## 封装的挺赞的
* BaseModel： 	基本的业务层，主要是封装了post 和get等网络传输方法
* BasePresenter： presenter是有共同特点：必传入一个视图接口和一个业务接口，所以这里定义了一个抽象的BasePresenter

