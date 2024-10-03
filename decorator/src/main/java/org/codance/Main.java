package org.codance;

/**
 * @author zhaoxg
 * @date 2024/10/3 10:59
 */
public class Main {
    public static void main(String[] args) {
        CompressDataSourceDecorator compressDataSourceDecorator = new CompressDataSourceDecorator(new DataSourceDecorator(new FileDataSource()));
        compressDataSourceDecorator.write("hhhh");
        System.out.println(compressDataSourceDecorator.read());
    }
}

interface DataSource {
    void write(String content);
    String read();
}

class FileDataSource implements DataSource {
    private String content;

    @Override
    public void write(String content) {
        this.content = content;
    }

    @Override
    public String read() {
        return content;
    }
}

class DataSourceDecorator implements DataSource {
    private DataSource dataSource;

    public DataSourceDecorator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void write(String content) {
        dataSource.write(content);
    }

    @Override
    public String read() {
        return dataSource.read();
    }
}

class CompressDataSourceDecorator extends DataSourceDecorator {

    public CompressDataSourceDecorator(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void write(String content) {
        super.write(compress(content));
    }

    @Override
    public String read() {
        return deCompress(super.read());
    }

    private String compress(String content) {
        return content + "compressed";
    }

    private String deCompress(String content) {
        return content.replace("compressed", "");
    }
}