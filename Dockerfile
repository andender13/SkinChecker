# Используйте базовый образ Ubuntu
FROM ubuntu:22.04

# Установите рабочую директорию
WORKDIR /app

# Установите зависимости
RUN apt-get update && \
    apt-get install -y wget gnupg2 unzip openjdk-17-jdk

# Добавьте репозиторий Google Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list' && \
    apt-get update

# Установите Google Chrome
RUN apt-get install -y google-chrome-stable

# Загрузите и установите нужную версию ChromeDriver
RUN wget https://storage.googleapis.com/chrome-for-testing-public/127.0.6533.88/linux64/chromedriver-linux64.zip -O chromedriver_linux64.zip && \
    ls -l chromedriver_linux64.zip && \
    unzip chromedriver_linux64.zip && \
    mv chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
    chmod +x /usr/local/bin/chromedriver && \
    rm chromedriver_linux64.zip

# Копируйте JAR-файл приложения в рабочую директорию
COPY target/TradeItGGSkinChecker-0.0.1-SNAPSHOT.jar app/checker.jar

# Установите переменные окружения для ChromeDriver
ENV CHROME_DRIVER_PATH=/usr/local/bin/chromedriver

# Команда для запуска вашего приложения
ENTRYPOINT ["java", "-jar", "app/checker.jar"]
