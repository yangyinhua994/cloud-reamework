package com.example.store;

import com.example.properties.DebeziumProperties;
import io.debezium.document.Document;
import io.debezium.document.DocumentReader;
import io.debezium.document.DocumentWriter;
import io.debezium.relational.history.AbstractDatabaseHistory;
import io.debezium.relational.history.DatabaseHistoryException;
import io.debezium.relational.history.HistoryRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FileDatabaseHistoryStore extends AbstractDatabaseHistory {

    private final Path filePath;

    public FileDatabaseHistoryStore() {
        String fileName = "debezium-db-history-" + DebeziumProperties.getInstance().getServiceName() + ".log";
        this.filePath = Paths.get(System.getProperty("java.io.tmpdir"), fileName);
    }

    @Override
    protected void storeRecord(HistoryRecord historyRecord) throws DatabaseHistoryException {
        try {
            String recordStr = DocumentWriter.defaultWriter().write(historyRecord.document());
            Files.write(filePath, (recordStr + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new DatabaseHistoryException("Failed to write history record to file", e);
        }
    }

    @Override
    protected void recoverRecords(Consumer<HistoryRecord> consumer) {
        if (!Files.exists(filePath)) return;

        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                try {
                    Document document = DocumentReader.defaultReader().read(line);
                    HistoryRecord record = new HistoryRecord(document);
                    consumer.accept(record);
                } catch (Exception ignored) {
                    // 忽略无法解析的记录
                }
            });
        } catch (IOException ignored) {
            // 忽略读取错误
        }
    }

    @Override
    public boolean exists() {
        return Files.exists(filePath) && Files.isReadable(filePath) && getFileLineCount() > 0;
    }

    private long getFileLineCount() {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.count();
        } catch (IOException e) {
            return 0;
        }
    }

    @Override
    public boolean storageExists() {
        return true; // 文件系统始终可用
    }
}
