# RxWeather

Personal pet project aimed to illustrate the use of the following android specific frameworks/libraries/tools: 

- [x] AndroidRx / JavaRx (Reactive programming)
- [x] Dagger2 (Dependency Injection)
- [x] Retrofit 2
- [x] Realm io / SQLite

The architecture for this applciation is based on the following repository:
https://github.com/android10/Android-CleanArchitecture/

<img src="https://lh3.googleusercontent.com/jC-E62Ejhh_EIXfm48K3X_sPJKPEdF6URAMxmEMZfWD1PHFExksB85lUxARwrN4AljA=h900-rw" width="400">
<img src="https://lh3.googleusercontent.com/b-_GbMh6FRNUJbutow7XNgTPrWVVTZrvKbf-dVY0083QLbTIPZu-3zO0U80dPq5Be3Im=h900-rw" width="400">

* I've originally intended to use Realm.io as the persistence mechanism for this project.
I've changed my mind and used SQLite insted due to several issues I've had trying to use Realm.
1 - Cannot access objects across threads (I did not want to query the db on the main thread)
2 - I've had problems on this and on other projects When storing nested RealmObjects, i.e leaks
3 - If you want to persist a specific class, said class Must Inherit from RealmObject *directly*
4 - Persisting lists on realm is not fully supported at the moment

Avilable on the PlayStore: https://play.google.com/store/apps/details?id=com.feresr.weather

The MIT License (MIT)

Copyright (c) [year] [fullname]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
