import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WorkingDirectory {
    private static WorkingDirectory instance;
    private String directoryName;

    private WorkingDirectory(String directoryName) {
        this.directoryName = directoryName;
    }

    public static WorkingDirectory getInstance(String directoryName) throws FileNotFoundException {
        if (!new File(directoryName).exists())
            throw new FileNotFoundException("Такой директории не существует");

        if (instance == null) {
            instance = new WorkingDirectory(directoryName);
        }
        return instance;
    }

    public String getDirectoryFiles() {
        String directoryFiles = "";
        String[] files = new File(instance.directoryName).list();

        if (files == null || files.length == 0)
            directoryFiles = directoryFiles.concat("Текущая директория пустая");
        else {
            for (int i = 0; i < files.length; ++i)
                directoryFiles = directoryFiles.concat(String.format("%d) %s", i + 1, files[i]) + "\n");
        }

        return directoryFiles;
    }

    public String getDirectoryFiles(String extension) throws IOException {
        String directoryFiles = "";
        String[] files = new File(instance.directoryName).list();

        if (files == null || files.length == 0)
            directoryFiles = directoryFiles.concat("Текущая директория пустая");
        else {
            for (int i = 0, j = 0; i < files.length; ++i) {
                if (new File(instance.directoryName + "\\" + files[i]).isFile()
                        && getExtension(files[i]).equals(extension))
                    directoryFiles = directoryFiles.concat(String.format("%d) %s", ++j, files[i]) + "\n");
            }
        }

        return directoryFiles;
    }

    public String getParentDirectoryName() {
        return new File(instance.directoryName).getParent();
    }

    public void goToParentDirectory() {
        File parentDir = new File(instance.directoryName).getParentFile();

        new File(instance.directoryName).renameTo(
                new File(parentDir.getAbsolutePath() + "\\" + parentDir.getName()));

        instance.directoryName = parentDir.getAbsolutePath();
    }

    public void goToChildDirectory(String dirName) throws FileNotFoundException {
        if (!existsChild(dirName))
            throw new FileNotFoundException("Такого подкаталога не существует");

        File currDir = new File(instance.directoryName);
        String newName = currDir.getParentFile().getAbsolutePath() + "\\" + dirName;
        currDir.renameTo(new File(newName));
        instance.directoryName = newName + "\\" + dirName;
    }

    public boolean existsChild(String fileName) {
        String[] files = new File(instance.directoryName).list();

        for (String file : files) {
            if (file.equals(fileName))
                return true;
        }

        return false;
    }

    public void deleteSubdirectories() {
        String[] files = new File(instance.directoryName).list();

        if (files == null || files.length == 0)
            System.out.println("Текущая директория пустая");
        else {
            for (String file : files) {
                if (new File(instance.directoryName + "\\" + file).isDirectory())
                    deleteSubdirs(instance.directoryName + "\\" + file);
            }
        }
    }

    public void createDirectory(String dirName) {
        new File(instance.directoryName + "\\" + dirName).mkdir();
    }

    public void printSubdirectories() {
        printSubdirs(instance.directoryName, 0);
    }

    public boolean existsDirectory(String dirName) {
        return existsDir(instance.directoryName, dirName);
    }


    private void deleteSubdirs(String dir) {
        String[] files = new File(dir).list();

        if (files != null) {
            for (String file : files) {
                String currFilePath = dir + "\\" + file;

                if (new File(currFilePath).isDirectory())
                    deleteSubdirs(currFilePath);
                else
                    new File(currFilePath).delete();
            }
        }

        new File(dir).delete();
    }

    private void printSubdirs(String dir, int nestingLevel) {
        String[] files = new File(dir).list();

        if (files != null) {
            for (String file : files) {
                String currFile = dir + "\\" + file;

                if (new File(currFile).isDirectory()) {
                    System.out.println(repeatStr("\t", nestingLevel) + file);
                    printSubdirs(currFile, nestingLevel + 1);
                }
            }
        }
    }

    private String repeatStr(String str, int count) {
        String repeat = "";

        for (int i = 0; i < count; ++i)
            repeat = repeat.concat("│" + str);

        return repeat;
    }

    private boolean existsDir(String currDir, String dirToFind) {
        String[] files = new File(currDir).list();

        if (files != null) {
            for (String file : files) {
                String currFile = currDir + "\\" + file;

                if (new File(currFile).isDirectory()) {
                    if (dirToFind.equals(file))
                        return true;
                    else {
                        if (existsDir(currFile, dirToFind))
                            return true;
                    }
                }
            }
        }

        return false;
    }

    public String getExtension(String filePath) throws IOException
    {
        if (filePath.indexOf('.') < 0 || filePath.indexOf('.') == filePath.length() - 1)
            throw new FileNotFoundException("Incorrect file name input");

        return filePath.substring(filePath.lastIndexOf('.') + 1);
    }

}
