# PokerPlanningRusBot

---
## Перед запуском приложения 

Для запуска базы данных (Redis) в Docker выполните в терминале IDEA:

    docker-compose up -d

## Настройка телеграм бота

1. В properties необходимо указать корректных webhook (**telegram.webhookPath**)
2. Необходимо указать этот же webhook для телеграмма, выполнив команду 
https://api.telegram.org/bot<telegram.botToken>/setWebhook?url=<telegram.webhookPath>
## Аккаунты администраторов:

Прописаны в properties

---

