# Контекст проекта ShiftTap Mod

## Описание
Модификация для Minecraft 1.21.11 (Fabric), представляющая собой Shift-Tap PvP модуль. Позволяет автоматически зажимать клавишу Sneak (шифт) при ударе по сущности. Все чит-функции исходного клиента полностью удалены. Дополнительно встроен легитный AutoSprint через игровые настройки.

## Стек технологий
- Minecraft: 1.21.11
- Loader: Fabric
- Yarn Mappings: 1.21.11+build.4
- Loom: 1.14-SNAPSHOT
- Java: 21
- Gradle
- Lombok

## Архитектура и ключевые модули
- `com.client.Client`: Главный класс инициализации мода.
- `com.client.systems.module.combat.ShiftTapModule`: Основной модуль, содержащий настройки (ticksAmount, onlyInAir, onlyOnGround) и логику автоматического приседания при атаке (с проверками на нахождение в воде/лаве/паутине и проверкой условий нахождения игрока в воздухе/на земле). Включает сохранение и загрузку конфигурации в `config/shifttap.json` с помощью библиотеки Gson.
- `com.client.systems.module.movement.AutoSprintModule`: Модуль автоматического спринта, который каждый тик переводит sprintKey в зажатое состояние (`setPressed(true)`), имитируя удержание клавиши игроком. Состояние активности модуля сохраняется в `config/autosprint.json`.
- `com.client.mixin.client.ClientPlayerInteractionManagerMixin`: Перехватывает вызов `attackEntity` для оповещения ShiftTapModule об ударе.
- `com.client.gui.screen.ClickGuiScreen`: Интерактивное меню настроек мода. Открывается по клавише `J`. Содержит кнопки включения/выключения модулей ShiftTap и AutoSprint, управления тиками и настройки условий срабатывания ShiftTap. Все обращения к `textRenderer` переведены на статический `mc.textRenderer` во избежание NPE.

## Статус сборки
- Успешно компилируется без ошибок через `./gradlew compileJava`. Выходной файл собирается в формате `shifttap-fabric-1.1.5+mc1.21.11.jar`.
