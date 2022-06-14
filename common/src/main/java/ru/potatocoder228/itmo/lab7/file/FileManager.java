package ru.potatocoder228.itmo.lab7.file;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import ru.potatocoder228.itmo.lab7.data.Coordinates;
import ru.potatocoder228.itmo.lab7.data.Dragon;
import ru.potatocoder228.itmo.lab7.data.DragonCave;
import ru.potatocoder228.itmo.lab7.data.DragonType;
import ru.potatocoder228.itmo.lab7.exceptions.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FileManager implements ReaderWriter {
    public static LinkedHashSet<Integer> idList = new LinkedHashSet<>();
    private InputStream inputStream;
    private String path;
    private File file;
    private LinkedList<Dragon> collection = new LinkedList<>();
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name;
    private long x_1;
    private Double y_1;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private int age; //Значение поля должно быть больше 0
    private String description;
    private Boolean speaking;
    private DragonType type;
    private DragonCave cave; //Поле не может быть null

    public FileManager(String pth) {
        path = System.getenv(pth);
        String[] checkPaths = path.split(";");
        if (checkPaths.length > 1) {
            path = null;
            System.out.println("Некорректная переменная окружения.");
        }
        path = path.replace(";", "");
    }

    public FileManager() {
        path = null;
    }

    public void setPath(String pth) {
        path = pth;
    }

    @Override
    public String read() throws FileException {
        String str = "";
        StringBuilder string = new StringBuilder();
        try {
            if (path == null) throw new EmptyPathException("Не указан путь к файлу.");
            InputStreamReader reader;

            File file = new File(path);
            if (!file.exists()) throw new FileNotExistsException("Файл не существует или него недостаточно прав.");
            if (!file.canRead()) throw new FileWrongPermissionsException("Не могу прочитать файл.");
            inputStream = new FileInputStream(file);
            reader = new InputStreamReader(inputStream);
            int currectSymbol;
            while ((currectSymbol = reader.read()) != -1) {
                string.append(((char) currectSymbol));
            }
            reader.close();
        } catch (IOException e) {
            throw new FileException("Не могу прочитать файл.");
        }
        str += string.toString();
        return str;
    }

    public LinkedList<Dragon> parseFile() {
        try {
            boolean uncorrectObject;
            if (path == null) throw new EmptyPathException("Не указан путь к файлу.");
            File file = new File(path);
            if (!file.exists()) throw new FileNotExistsException("Файл не существует");
            if (!file.canRead()) throw new FileWrongPermissionsException("Невозможно прочитать файл.");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            String[] tags = new String[]{"name", "id", "coordinate_x", "coordinate_y", "creationDate", "age", "description", "speaking", "type", "cave"};
            String[] values = new String[10];
            int counter = document.getDocumentElement().getElementsByTagName("Dragon").getLength();
            for (int k = 0; k < counter; k++) {
                for (int i = 0; i < tags.length; i++) {
                    try {
                        NodeList dragonElements = document.getDocumentElement().getElementsByTagName(tags[i]);
                        String value = dragonElements.item(k).getFirstChild().toString();
                        values[i] = value.substring(8, value.length() - 1);
                    } catch (NullPointerException e) {
                        values[i] = "null";
                    }
                }
                uncorrectObject = validateElements(values);
                Dragon newDragon = new Dragon(name, coordinates, age, description, speaking, type, cave);
                newDragon.setCreationDate();
                newDragon.setId();
                if (uncorrectObject && !newDragon.validate()) {
                    System.out.println("Объект некорректен и не будет добавлен в коллекцию.");
                } else {
                    collection.add(newDragon);
                }
            }
        } catch (FileNotFoundException e) {
            try {
                file = new File(path);
                create(file);
                FileOutputStream fos = new FileOutputStream(path);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                String text = "<Dragons>\n</Dragons>";
                byte[] buffer = text.getBytes();
                bos.write(buffer, 0, buffer.length);
                bos.flush();
                System.out.println("Файл отсутствовал, но был создан.");
            } catch (CannotCreateFileException | IOException ex) {
                path = null;
                System.out.println("Ошибка доступа. Чтение и сохранение в файл невозможно.");
            }
        } catch (SAXParseException e) {
            file = new File(path);
            if (file.isFile()) {
                System.out.println("\n0 объектов будет добавлено в коллекцию.");
            } else {
                System.out.println("В переменной окружения содержится путь на директорию. Сохранение будет невозможно.");
                path = null;
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("\nУ файла неподдерживаемая кодировка. Пожалуйста, измените её и попробуйте ввести переменную окружения снова.");
            path = null;
        } catch (AccessDeniedException e) {
            System.out.println("\nНа файл с информацией о коллекции недостаточно прав доступа");
            path = null;
        } catch (NullPointerException e) {
            System.out.println("\nНекорректная переменная окружения.");
            path = null;
        } catch (IOException ex) {
            System.out.println("Ошибка чтения. ");
            path = null;
        } catch (FileException | ParserConfigurationException e) {
            System.out.println("Ошибка при работе с файлом.");
            path = null;
        } catch (SAXException e) {
            System.out.println("Ошибка при анализе данных в файле.");
            path = null;
        } catch (NoSuchElementException e) {
            System.out.println("Некорректное имя переменной окружения.");
            path = null;
        }
        return collection;
    }

    private void create(File file) throws CannotCreateFileException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new CannotCreateFileException("Создание файла невозможно.");
        }
    }

    @Override
    public void write(String str) throws FileException {
        try {
            if (path == null) throw new EmptyPathException("Не указан путь к файлу.");
            File file = new File(path);
            if (!file.exists()) {
                create(file);
            }
            if (!file.canWrite()) throw new FileWrongPermissionsException("Запись в файл невозможна");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            throw new FileException("Нехватает прав доступа к файлу.");
        }
    }

    public boolean validateElements(String[] arg) {
        boolean uncorrectObject = false;
        try {
            Integer lastId = null;
            for (int i = 0; i < arg.length; i++) {
                if (i == 0) {
                    name = arg[0];
                } else if (i == 1) {
                    lastId = Integer.parseInt(arg[i]);
                    if (idList.contains(lastId)) {
                        throw new WrongIdException();
                    } else {
                        id = lastId;
                    }
                } else if (i == 2) {
                    long lastX = Long.parseLong(arg[i]);
                    if (lastX > 436) {
                        throw new WrongCoordinatesException();
                    } else {
                        x_1 = lastX;
                    }
                } else if (i == 3) {
                    double lastY = Double.parseDouble(arg[i]);
                    if (lastY > 436) {
                        throw new WrongCoordinatesException();
                    } else {
                        y_1 = lastY;
                        coordinates = new Coordinates(x_1, y_1);
                    }
                } else if (i == 4) {
                    String[] date_and_time = arg[i].split("\\s+");
                    String[] time = date_and_time[0].split(":");
                    String[] date = date_and_time[1].split("\\.");
                    int day = Integer.parseInt(date[0]);
                    int month = Integer.parseInt(date[1]);
                    int year = Integer.parseInt(date[2]);
                    int hour = Integer.parseInt(time[0]);
                    int minute = Integer.parseInt(time[1]);
                    creationDate = LocalDateTime.of(year, month, day, hour, minute);
                } else if (i == 5) {
                    int lastAge = Integer.parseInt(arg[i]);
                    if (lastAge <= 0) {
                        throw new WrongDateException();
                    } else {
                        age = lastAge;
                    }
                } else if (i == 6) {
                    description = arg[i];
                } else if (i == 7) {
                    if (!arg[i].equals("true") && !arg[i].equals("false")) {
                        throw new WrongSpeakingException();
                    } else {
                        speaking = Boolean.parseBoolean(arg[i]);
                    }
                } else if (i == 8) {
                    type = DragonType.valueOf(arg[i]);
                } else if (i == 9) {
                    cave = new DragonCave(Float.parseFloat(arg[9]));
                }
                idList.add(lastId);
            }
        } catch (NumberFormatException e) {
            System.out.println("Одно из полей объекта имеет несоответствующее значение.");
            uncorrectObject = true;
        } catch (WrongAgeException e) {
            System.out.println("У объекта некорректный возраст.");
            uncorrectObject = true;
        } catch (WrongIdException e) {
            System.out.println("У объекта некорректный id.");
            uncorrectObject = true;
        } catch (WrongCoordinatesException e) {
            System.out.println("У объекта некорректные координаты.");
            uncorrectObject = true;
        } catch (WrongSpeakingException e) {
            System.out.println("У объекта некорректное поле speaking.");
            uncorrectObject = true;
        } catch (WrongTypeException e) {
            System.out.println("У объекта некорректный тип.");
            uncorrectObject = true;
        } catch (WrongDateException e) {
            System.out.println("У объекта некорректная дата создания.");
            uncorrectObject = true;
        }
        return uncorrectObject;
    }

    public void writeObjects(LinkedList<Dragon> list) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            String text = "<Dragons>";
            StringBuilder string = new StringBuilder();
            for (Dragon dragon : list) {
                String[] fields = dragon.getAllFields();
                string.append("\n\t<Dragon>");
                string.append("\n\t\t<name>").append(fields[0]).append("</name>");
                string.append("\n\t\t<id>").append(fields[1]).append("</id>");
                string.append("\n\t\t<coordinate_x>").append(fields[2]).append("</coordinate_x>");
                string.append("\n\t\t<coordinate_y>").append(fields[3]).append("</coordinate_y>");
                string.append("\n\t\t<creationDate>").append(fields[4]).append("</creationDate>");
                string.append("\n\t\t<age>").append(fields[5]).append("</age>");
                string.append("\n\t\t<description>").append(fields[6]).append("</description>");
                string.append("\n\t\t<speaking>").append(fields[7]).append("</speaking>");
                string.append("\n\t\t<type>").append(fields[8]).append("</type>");
                string.append("\n\t\t<cave>").append(fields[9]).append("</cave>");
                string.append("\n\t</Dragon>");
            }
            text += string.toString();
            text += "\n</Dragons>";
            byte[] buffer = text.getBytes();
            bos.write(buffer, 0, buffer.length);
            bos.flush();
            System.out.println("Сохранение успешно проведено.");
        } catch (IOException e) {
            System.out.println("Ошибка чтения.");
        } catch (NullPointerException e) {
            System.out.println("Вы не ввели переменную окружения, в которой лежит путь к файлу.");
            System.out.println("Сохранение невозможно.");
        }
    }
}