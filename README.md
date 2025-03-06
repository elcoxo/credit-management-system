# credit-management-system

АРМ менеджер по оформлению кредита.

Общий процесс выглядит следующим образом:
1) оформление заявки на кредит
2) принятие решения по кредиту
3) подписание кредитного договора

![index](https://github.com/user-attachments/assets/ce27d6a7-6005-42de-a1f5-2e79711fe0a2)

## Используемый стек технологий:

- Java 21
- Maven
- Spring Boot 3.4.3
- Spring MVC
- Hibernate (без Spring Data)
- PostgreSQL 17
- Thymeleaf
- Tailwind

## Настройка конфигураций:
### Зависимости
Установка зависимостей Maven

```
mvn clean package
```

Установка npm-зависимостей

```
npm install
```

### База данных:

Создайте PostgreSQL базу данных и добавьте учетные данные в `/resources/application.yaml`

По умолчанию сейчас :

```yaml
  datasource:
    url: jdbc:postgresql://localhost:5432/credit-ms
    username: root
    password: 12345
```

## Модели


Схема связей:

```
(Client) 1 ─── 1 (ClientEmployment)
(Client) 1 ─── 1 (ClientIdentity)
(Client) 1 ─── 1 (CreditApplication) 1 ─── 1 (CreditContract)
```
### Client
Основная информациа о клиенте.

Поля:

- `firstName`, `lastName`, `middleName` — ФИО клиента.
- `dateOfBirth` — дата рождения.
- `maritalStatus` — семейное положение.
- `phoneNumber` — номер телефона.

Связи Один-к-одному:

- `CreditApplication` (Кредитная заявкой) — один клиент подаёт одну заявку.
- `ClientEmployment` (Занятость) — одна запись о текущем месте работы.
- `ClientIdentity` (Паспортные данные) — одна запись с паспортными/адресными данными.

### ClientEmployment
Хранит данные о работе клиента.

Поля:

- `companyName` — название компании.
- `jobPosition` — должность.
- `startDate`, `endDate` — даты начала и окончания работы.

Связи с клиентов Один-к-одному:

- `client` — ссылается на Client

### ClientIdentity
Содержит паспортные данные клиента.

Поля:

- `address` — адрес проживания.
- `identityNumber` — номер паспорта/ИНН.

Связи Один-к-одному:

- `client` — ссылается на `Client`

### CreditApplication
Хранит данные о заявках на кредит.

Поля:

- `requestedAmount` — запрашиваемая сумма.
- `status` — статус заявки (`APPROVED`, `REJECTED`, `INPROGRESS`).

Связи Один-к-одному:

- `client` — заявка привязана к одному клиенту.
- `contract` — одна заявка может привести к одному кредитному контракту.

### CreditContract
Документ, фиксирующий условия одобренного кредита.

Поля:

- `approvedDays` — срок кредита в днях.
- `approvedAmount` — одобренная сумма.
- `approvedDate` — дата одобрения.
- `signatureCreated` — дата подписи контракта.
- `signatureStatus` — статус подписания (`SIGNED`, `NOT_SIGNED`).

Связи Один-к-одному:

- `application` — контракт привязан к одной заявке.

## Дерево проекта

```
src/main/java/ru/temets/credit_management_system
│
├── entity/                   // Database entities
│   ├── Client.java
│   ├── ClientEmployment.java
│   ├── ClientIdentity.java
│   ├── CreditApplication.java
│   └── CreditContract.java
│
├── dto/                       
│   ├── request/               // DTOs to receive data
│   │   └── ClientRequestDTO.java
│   │
│   └── response/              // DTOs to send data
│       ├── ClientResponseDTO.java/
│       ├── CreditContractResponseDTO.java/
│       └── CreditResponseDTO.java/
│
├── mapper/                    // Converters between Entity and DTOs
│   ├── ClientMapper.java
│   └── CreditMapper.java
│
├── repository/                // Data access
│   ├── ClientDAO.java
│   ├── CreditApplicationDAO.java
│   └── CreditContractDAO.java
│
├── service/                   // Business logic
│   ├── ClientService.java     
│   └── CreditService.java     
│
└── controller/
    ├── mvc/                   // MVC Controllers
    │   ├── ClientControllerMVC.java                
    │   └── CreditApplicationControllerMVC.java     
    ├── ClientController.java  // REST Controllers
    └── CreditApplicationController.java
```

## Эндпроинты


### Создание новой заявки/клиента":

```
POST localhost:8080/api/v1/clients/add
```
Пример Json файла: 
```json
{
    "firstName": "Иван",
    "lastName": "Иванов",
    "middleName": "Иванович",
    "dateOfBirth": "2000-01-15",
    "maritalStatus": "SINGLE",
    "phoneNumber": 9994442211,
    "address": "г. Москва, ул. Ленина, д. 1",
    "identityNumber": 1234567890,
    "companyName": "ООО Рога и Копыта",
    "jobPosition": "Менеджер",
    "startDate": "2020-01-15",
    "endDate": null,
    "requestedAmount": "50000.00"
}
```

### Вывод всех клиентов:

```
GET localhost:8080/api/v1/clients
```

![clients](https://github.com/user-attachments/assets/82228ff0-b4f7-4a82-acb7-eee7af9dbd56)

### Вывод одного клиента заявок:

```
GET localhost:8080/api/v1/clients/{id}
```
![client](https://github.com/user-attachments/assets/41c8df71-cfc9-474b-a250-bdc055fc30b5)


### Удалить информацию о клиенте:

```
DELETE localhost:8080/api/v1/clients/{id}
```

### Поиск клиентов по ФИО, телефону и паспортным данным:

```
GET localhost:8080/api/v1/clients/searc
```
Пример с именем `Петр` И номером `9994442222`
```
GET localhost:8080/api/v1/clients/search?firstName=Петр&phoneNumber=9994442222
```

### Вывод заявок на кредит:

Вывод всех заявок
```
GET localhost:8080/api/v1/applications
```
Вывод всех заявок со статусом одобрения
```
GET localhost:8080/api/v1/applications?status=APPROVED
```
Вывод всех заявок со статусом со статусом подписания
```
GET localhost:8080/api/v1/applications?sign=SIGNED
```

### Подписать кредитный договор:

```
POST http://localhost:8080/api/v1/clients/{id}/sign
```
Пример ответа
```
"creditApplication": {
            "id": 3,
            "requestedAmount": 50000.00,
            "status": "APPROVED",
            "creditContract": {
                "id": 1,
                "approvedDays": 332,
                "approvedAmount": 45665.81,
                "approvedDate": "2025-03-06T09:30:44.078498",
                "signatureCreated": "2025-03-06T09:33:36.005475",
                "signatureStatus": "SIGNED"
            }
        }
```
![sign](https://github.com/user-attachments/assets/f63f1e9a-1ff5-4a86-b993-42f852fa8b48)

сделано с любовью :)

