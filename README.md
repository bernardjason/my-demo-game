# Demo Game

```commandline
./gradlew desktop:run
```

```commandline
./gradlew html:superDev
```
then visit http://localhost:8080/index.html and http://127.0.0.1:9876/

```commandline
./gradlew desktop:dist
```
```commandline
./gradlew html:dist
```

then 
```commandline
cd html/build/dist
python3 -m http.server
```
and visit http://127.0.0.1:8000/
