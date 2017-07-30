# opentrends-test

Opentrends technical test for the Android Developer position. I took the time to implement two flavors of the app:

**MVP flavor**: Implementation with MVP design pattern. Communication with *View* layer is done through an interface

**MVVM flavor**: Implementation with MVVM design pattern using a reactive approach thanks to *RxJava*. Communication with *View* layer is done by exposing streams/observables

- **Dagger2** for DI
- **Retrofit2** for consuming Marvel API
- **Fresco** for image loading & caching
- **RxBinding** by *Jake Wharton* of *Square Engineering* to map observables to UI events (mostly onclick listeners for this project)
- **Retrolambda** for using Java 8's lambda expressions on Java 7

Thank you very much for the opportunity!
