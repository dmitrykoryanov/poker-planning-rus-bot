package ru.sber.ppbot.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sber.ppbot.service.AdminService;
import ru.sber.ppbot.service.DistributionService;
import ru.sber.ppbot.service.TaskService;
import ru.sber.ppbot.service.UserService;
import ru.sber.ppbot.util.KeyboardsFactory;
import ru.sber.ppbot.util.MessageUtil;
import ru.sber.ppbot.util.SendMessageFactory;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

/**
 * <p>Enum with menu options and overloaded methods</p>
 *
 * @author Dmitry Koryanov
 */
@Slf4j
public enum MainMenu {

    START("/start", 8) {
        @Override
        public void processOption(Message message) {
        }

        @Override
        public void processAddOption(Message message) {
        }

        @Override
        public SendMessage getSendMessage(Message message) {

            currentOption = this.getOptionNumber();

            SendMessage sendMessage = SendMessageFactory.getSendMessageInstance(message.getChatId().toString(),
                    helpMessage + "Доступные действия:");
            sendMessage.setReplyMarkup(KeyboardsFactory.getMainKeyBoard());

            return sendMessage;
        }
    },
    ADD_ADMIN("/addAdmin", 1) {
        @Override
        public void processOption(Message message) {
            if (getCurrentOption() == ADD_ADMIN.getOptionNumber()) {
                getAdminService().addAdmin(Admin.builder().name(message.getText()).build());
                setCurrentOption(START.getOptionNumber());
                setHelpMessage("Администратор *" + message.getText() + "* был успешно добавлен. \n");
            }
        }

        @Override
        public void processAddOption(Message message) {
        }

        @Override
        public SendMessage getSendMessage(Message message) {
            currentOption = this.getOptionNumber();
            return SendMessageFactory.getSendMessageInstance(message.getChatId().toString(),
                    "Введите username нового администратора:");
        }
    },
    DEL_ADMIN("/delAdmin", 4) {
        @Override
        public void processOption(Message message) {
            if (getCurrentOption() == DEL_ADMIN.getOptionNumber()) {
                getAdminService().findById(MessageUtil.getIdFromMessage(message))
                        .ifPresentOrElse(a -> {
                                    getAdminService().delete(a);
                                    setHelpMessage("Администратор *@" + MessageUtil.getIdFromMessage(message)
                                            + "* был успешно удалён. \n");
                                },
                                () -> setHelpMessage("Администратор *" + message.getText()
                                        + "* не найден в списке администраторов и поэтому не был удалён. \n"));
                setCurrentOption(START.getOptionNumber());
            }
        }

        @Override
        public void processAddOption(Message message) {
            getAdminService().getAdminsList().forEach(
                    a -> setHelpMessage(getHelpMessage() + "\n*/" + a.getName() + "*"));
        }

        @Override
        public SendMessage getSendMessage(Message message) {
            currentOption = this.getOptionNumber();
            return SendMessageFactory.getSendMessageInstance(message.getChatId().toString(),
                    "Выберите администратора для удаления:" + helpMessage);
        }
    },
    ADD_USER("/addUser", 2) {
        @Override
        public void processOption(Message message) {
            if (getCurrentOption() == ADD_USER.getOptionNumber()) {
                getUserService().addUser(User.builder().username(message.getText()).build());
                setCurrentOption(START.getOptionNumber());
                setHelpMessage("Пользователь *" + message.getText() + "* был успешно добавлен. \n");
            }
        }

        @Override
        public void processAddOption(Message message) {
        }

        @Override
        public SendMessage getSendMessage(Message message) {
            currentOption = this.getOptionNumber();
            return SendMessageFactory.getSendMessageInstance(message.getChatId().toString(),
                    "Введите username нового пользователя:");
        }
    },
    DEL_USER("/delUser", 5) {
        @Override
        public void processOption(Message message) {
            if (getCurrentOption() == DEL_USER.getOptionNumber()) {
                this.getUserService().findById(message.getText().substring(1))
                        .ifPresentOrElse(u -> {
                                    this.getUserService().delete(u);
                                    setHelpMessage("Пользователь *@" + MessageUtil.getIdFromMessage(message)
                                            + "* был успешно удалён. \n");
                                },
                                () -> setHelpMessage("Пользователь *" + message.getText()
                                        + "* не найден в списке пользователей и поэтому не был удалён. \n"));
                setCurrentOption(START.getOptionNumber());
            }
        }

        @Override
        public void processAddOption(Message message) {
            this.getUserService().findAll().forEach(
                    u -> setHelpMessage(getHelpMessage() + "\n*/" + u.getUsername() + "*"));
        }

        @Override
        public SendMessage getSendMessage(Message message) {
            currentOption = this.getOptionNumber();
            return SendMessageFactory.getSendMessageInstance(message.getChatId().toString(),
                    "Выберите пользователя для удаления:" + helpMessage);
        }
    },
    ADD_TASK("/addTask", 3) {
        @Override
        public void processOption(Message message) {
            if (getCurrentOption() == ADD_TASK.getOptionNumber()) {
                getTaskService().addTask(Task.builder().name(message.getText()).build());
                setCurrentOption(START.getOptionNumber());
                setHelpMessage("Задача *" + message.getText() + "* был успешно добавлена. \n");
            }
        }

        @Override
        public void processAddOption(Message message) {
        }

        @Override
        public SendMessage getSendMessage(Message message) {
            currentOption = this.getOptionNumber();
            return SendMessageFactory.getSendMessageInstance(message.getChatId().toString(),
                    "Введите название задачи:");
        }
    },
    DEL_TASK("/delTask", 6) {
        @Override
        public void processOption(Message message) {
            if (getCurrentOption() == DEL_TASK.getOptionNumber()) {
                getTaskService().findById(Long.parseLong(message.getText().substring(1)))
                        .ifPresentOrElse(t -> {
                                    getTaskService().delete(t);
                                    setHelpMessage("Задача с id=" + MessageUtil.getIdFromMessage(message)
                                            + " был успешна удалена. \n");
                                },
                                () -> setHelpMessage("Задача c id=" + MessageUtil.getIdFromMessage(message)
                                        + " не найдена в списке задач и поэтому не была удалена. \n"));
                setCurrentOption(START.getOptionNumber());
            }
        }

        @Override
        public void processAddOption(Message message) {
            getTaskService().findAllTasks().forEach(t -> setHelpMessage(getHelpMessage() + "\n"
                    + "*/" + t.getId() + "* *" + t.getName() + "* "));
        }

        @Override
        public SendMessage getSendMessage(Message message) {
            currentOption = this.getOptionNumber();
            return SendMessageFactory.getSendMessageInstance(message.getChatId().toString(),
                    "Выберите задачу, которую необходимо удалить:" + helpMessage);
        }
    },
    POLL("/startPoll", 7) {
        @Override
        public void processOption(Message message) {
            if (getCurrentOption() == POLL.getOptionNumber()) {
                setHelpMessage(getDistributionService().distribute(message));
                setCurrentOption(START.getOptionNumber());
            }
        }

        @Override
        public void processAddOption(Message message) {
            getTaskService().findAllTasks().forEach(t ->
            {
                setHelpMessage(getHelpMessage() + "\n\n*/"
                        + t.getId() + "* *" + t.getName() + "* - <*" + t.getComplexity() + "*>.");

                setHelpMessage(getHelpMessage() + "\n Детализация оценок:");

                t.getComplexities().forEach(
                        (k, v) -> setHelpMessage(getHelpMessage() + "\n*@" +
                                k + "* : " + v.getText()));
            });
        }

        @Override
        public SendMessage getSendMessage(Message message) {
            currentOption = this.getOptionNumber();
            return SendMessageFactory.getSendMessageInstance(message.getChatId().toString(),
                    "Выберите задачу для голосования:" + helpMessage);
        }
    },
    LIST_TASKS("/listTasks", 9) {
        @Override
        public void processOption(Message message) {
        }

        @Override
        public void processAddOption(Message message) {
            getTaskService().findAllTasks().forEach(t ->
            {
                setHelpMessage(getHelpMessage() + "\n\n"
                        + t.getId() + " *" + t.getName() + "* - <*" + t.getComplexity() + "*>.");
                setHelpMessage(getHelpMessage() + "\n Детализация оценок:");
                t.getComplexities().forEach(
                        (k, v) -> setHelpMessage(getHelpMessage() + "\n*@" +
                                k + "* : " + v.getText()));

            });
        }

        @Override
        public SendMessage getSendMessage(Message message) {
            return SendMessageFactory.getSendMessageInstance(message.getChatId().toString(),
                    "Список задач с оценкой сложности:" + helpMessage);
        }
    };

    private final String text;

    private final int optionNumber;

    public int getOptionNumber() {
        return optionNumber;
    }

    private static int currentOption;

    public static int getCurrentOption() {
        return currentOption;
    }

    public static void setCurrentOption(int currentOption) {
        MainMenu.currentOption = currentOption;
    }

    private static String helpMessage = "";

    public static String getHelpMessage() {
        return MainMenu.helpMessage;
    }

    public static void setHelpMessage(String helpMessage) {
        MainMenu.helpMessage = helpMessage;
    }

    private MainMenu(String text, int optionNumber) {
        this.text = text;
        this.optionNumber = optionNumber;
    }

    public String getText() {
        return text;
    }

    //setvices to be attached to some options
    private AdminService adminService;

    private UserService userService;

    private TaskService taskService;

    private DistributionService distributionService;


    public abstract void processOption(Message message);

    public abstract void processAddOption(Message message);

    public abstract SendMessage getSendMessage(Message message);

    //getters & setters for private fields
    public DistributionService getDistributionService() {
        return distributionService;
    }

    public void setDistributionService(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    public UserService getUserService() {
        return userService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    protected void setUserService(UserService userService) {
        this.userService = userService;
    }

    protected void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    protected void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    //inner static helper class for setting some private fields for enum options
    @Component
    public static class Helper {

        @Autowired
        private AdminService adminService;

        @Autowired
        private UserService userService;

        @Autowired
        private TaskService taskService;

        @Autowired
        private DistributionService distributionService;

        @PostConstruct
        public void postConstruct() {
            for (MainMenu menuOption : EnumSet.allOf(MainMenu.class)) {
                menuOption.setAdminService(adminService);
                menuOption.setUserService(userService);
                menuOption.setTaskService(taskService);
                menuOption.setDistributionService(distributionService);
            }
        }

    }
}