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

## База данных:

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

