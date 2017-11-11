# 实现自定义控件生命周期的自我管理 例子:[CountdownView](https://github.com/genius158/ANC/blob/master/app/src/main/java/com/yan/anc/widget/CountdownView.kt)

## [DOME下载](https://github.com/genius158/ANC/raw/master/app-debug.apk)

## 说明（Android架构组件原码解析已经有人发过了，这里就不缀述了）
26.1.0的版本activity和fragment都已经实现了LifecycleOwner这个接口，就意味着以后都可以通过注册监听器组件的生命周期，从而可以实现控件生命周期的自我管理
<br/>
LifecycleRegistry是Lifecycle的子类，也是管理生命周期的核心组件，GenericLifecycleObserver这个接口则是生命周期直接响应的接口，所以我们要实现这个接口

## 编码[CountdownView](https://github.com/genius158/ANC/blob/master/app/src/main/java/com/yan/anc/widget/CountdownView.kt)(kotlin)

```
//第一步当然是实现GenericLifecycleObserver接口
class CountdownView(context: Context, attrs: AttributeSet) : View(context, attrs), GenericLifecycleObserver {
    init { //出始化

        getLifecycleOwner()?.lifecycle?.addObserver(this)//注册自己到activity或fragment的LifecycleRegistry中
    }

    //如果控件依附于fragment则调用这个方法，传入fragment
    fun attachFragment(fragment: Fragment) {
        this.fragment = fragment
    }

   //这个方法用于返回LifecycleOwner
    private fun getLifecycleOwner(): LifecycleOwner? {
        if (fragment == null) {
            if (context is LifecycleOwner) {
                return context as LifecycleOwner
            }
        } else {
            if (fragment is LifecycleOwner) {
                return fragment
            }
        }
        return null
    }

    //生命周期的回调，已下实现动画在activity处在后台的时候暂停，回前台则继续运行
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.e("onStateChanged", source.lifecycle.currentState.toString() + "   " + event)
        when (event) {
            Lifecycle.Event.ON_RESUME -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                valueAnimation.resume()
            }
            Lifecycle.Event.ON_PAUSE -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                valueAnimation.pause()
            }
            Lifecycle.Event.ON_DESTROY -> {//当activity或fragment被销毁是取消动画，移除监听
                cancel()
                source.lifecycle.removeObserver(this)
            }
        }
    }

}
```