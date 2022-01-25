(
  cd webinterface
  npm install
)

screen -S better_nudel_rewrite mvn compile test exec:java
