# 安卓动态内存分配算法演示系统

---

tags: [android,内存管理,算法,计算机组成与原理]

---

## 总体介绍
主存是CPU可以直接访问的信息空间，合理有效的使用它对提升计算机的计算能力与性能有很大帮助。
这个实验对内存分配和回收进行了深入的研究，认真完成这个试验之后对内存分配和管理有一个更加深入的了解。


 
## 实验要求
假定某机器存储空间大小为64KB。
编制一组函数分别用于在该空间中申请存储空间(以256字节为基本块,块数1-100可变)和释放使用完毕的存储空间。
编写一个程序反复申请（使用随机数确定每次申请的块数）、修改和释放存储空间，观察存储空间的变化。


<!-- more -->


## 实验目的
通过这次实验，我们可以进一步加深对可变分区存储机制的理解。更加深刻地认识存储器动态分配的认识。其中我着重学习了首次适应算法，下次适应算法，最佳适应算法和最坏适应算法。

![首页](http://7xq13x.com1.z0.glb.clouddn.com/S60118-184435.jpg)

## 实现功能详细介绍

### 动态显示内存
 
每次分配内存会有弹框提示，实时占用内存也会显示在屏幕上。

![菜单](http://7xq13x.com1.z0.glb.clouddn.com/S60118-184443.jpg)

### 水波动态图纹
灵感是来自360水平球。
所以当内存变化时有水波荡漾的特效。

	
## 基础知识	

 
1、首次适应算法（first fit）
FF算法要求空闲分区链以地址递增的次序链接。在分配内存时，从链首开始顺序查找，直至找到一个大小能男足要求的空闲分区位置；然后再按照作业的大小，从该分区中划出一块内存空间分配给请求者，余下的空闲分区仍留在空闲链中。若从链首直至链尾都不能找到一个能满足要求的分区，则此次内存分配失败，返回。但是，低址部分不断被划分，会留下许多难以利用的很小的空闲分区。
 

2、最佳适应算法（best fit）
所谓“最佳”是指每次为作业分配内存时，总是把能满足要求、又是最小的空闲分区分配给作业，避免“大材小用”。为了加速寻找，该算法要求将所有的空闲分区按其容量以从小到大的顺序形成一空闲分区链。这样，第一次找到的能满足要求的空闲区，必然是最佳的。这样，在存储器中会留下许多难以利用的小空闲区。

 
3、最坏适应算法（worst fit）
要扫描整个空闲分区表或链表，总是挑选一个最大的空闲区分割给作业使用，其优点是可使剩下的空闲区不至于太小，产生碎片的几率最小，对中小作业有力，查找效率很高。但是它会使存储器中缺乏大的空闲分区。
 

4、下次适应算法（next fit）



## 代码编写
 

算法核心部分我写了五个部分。


Drawer.java主要是构成界面，形成抽屉样式，分别五个是侧边栏的五项：


假定某机器存储空间大小为64KB。
以256字节为基本块,块数1-100可变。
64乘1024除256等于256，所以分256块，每次分配的块数从1到100随机不等。


## 实验结论

四种算法的比较
1、首次适应算法
算法倾向于使用内存中低地址部分的空闲分区，在高地址部分的空闲分区很少被利用，从而保留了高地址部分的大空闲区。显然为以后到达的大作业分配大的内 存空间创造了条件。缺点在于低址部分不断被划分，留下许多难以利用、很小的空闲区，而每次查找又都从低址部分开始，这无疑会增加查找的开销。

2、最佳适应算法
为了加速查找，该算法要求将所有的空闲区按其大小排序后，以递增顺序形成一个空白链。这样每次找到的第一个满足要求的空闲区，必然是最优的。孤立地看， 该算法似乎是最优的，但事实上并不一定。因为每次分配后剩余的空间一定是最小的，在存储器中将留下许多难以利用的小空闲区。同时每次分配后必须重新排序， 这也带来了一定的开销。

3、最坏适应算法
最坏适应算法与最佳适应算法的排序正好相反，它的队列指针总是指向最大的空闲区，在进行分配时，总是从最大的空闲区开始查寻。
　　该算法克服了最佳适应算法留下的许多小的碎片的不足，但保留大的空闲区的可能性减小了，而且空闲区回收也和最佳适应算法一样复杂。

4、下次适应算法
该算法是由首次适应算法演变而成的。在为进程分配内存空间时，不再每次从链首开始查找，而是从上次找到的空闲分区开始查找，直至找到一个能满足要求的空闲分区，并从中划出一块来分给作业。该算法能使空闲中的内存分区分布得更加均匀，但将会缺乏大的空闲分区。


这个实验对内存分配和回收进行了深入的研究，认真完成这个试验之后对内存分配和管理有一个更加深入的了解，特别是首次适应算法，下次适应算法，最佳适应算法和最坏适应算法这四种算法，更加深刻地认识存储器动态分配的认识。

觉得好就给个star吧_(:з」∠)_