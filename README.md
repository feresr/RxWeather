# RxWeather
Personal pet project aimed to illustrate the use of the following android specific frameworks/libraries/tools: AndroidRx, JavaRx, Dagger2, UnitTesting, RetroLambda, Retrofit2, Data Binding

<B>Current state</B><br>
AndroidRx / JavaRx <i>Implemented</i> ✓ <br>
[x] Dagger2 <i>Implemented</i> ✓ <br>
[x] Guava <i>Implemented</i> ✓ <br>
[ ] UnitTesting <i>To implement</i><br>
[ ] Mockito <i>To implement</i><br>
[x] RetroLambda <i>To implement</i><br>
[ ] ButterKnife <i>To implement</i><br>
[x] Retrofit2 <i>Implemented</i> ✓ <br>
[ ] Data Binding <i>To implement</i><br>
[x] Realm io / SQLite <i>Implemented</i> ✓ <br> *see below


The architecture for this applciation is loosely based on the following repository:
https://github.com/tehmou/rx-android-architecture
and
https://github.com/android10/Android-CleanArchitecture/

* I've originally intended to use Realm.io as the persistence mechanism for this project.
I've changed my mind and used SQLite insted due to several issues I've had trying to use Realm.
1 - Cannot access objects across threads (I did not want to query the db on the main thread)
2 - I've had problems on this and on other projects When storing nested RealmObjects, i.e leaks
3 - If you want to persist a specific class, said class Must Inherit from RealmObject *directly*
4 - Persisting lists on realm is not fully supported at the moment