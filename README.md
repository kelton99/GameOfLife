# Game of Life in Javafx

It is a simulation of Conways's game of life made using JavaFx.

## Building

Installing necessary dependencies

# ![](https://img.shields.io/badge/Debian-A81D33?style=for-the-badge&logo=debian&logoColor=white)
```bash
$ sudo apt update && sudo apt install -y git openjdk-21-jdk maven
```

# ![](https://img.shields.io/badge/Arch_Linux-1793D1?style=for-the-badge&logo=arch-linux&logoColor=white)
```bash
$ sudo pacman -S git jdk21-openjdk maven
```

Check if standard Java and Java Compiler are both set to 21 with
```bash
java -version
javac -version
```

Clone the repository, enter it and run the compilation command
```bash
$ git clone https://github.com/kelton99/GameOfLife.git
$ cd GameOfLife
```
To build and run
```bash
$ mvn clean javafx:run
```