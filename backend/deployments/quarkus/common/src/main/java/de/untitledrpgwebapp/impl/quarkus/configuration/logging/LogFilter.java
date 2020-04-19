package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PROTECTED)
public abstract class LogFilter {

  private final HttpTrafficLogger logger;

  protected LogFilter(HttpTrafficLogger logger) {
    this.logger = logger;
  }

  protected Object extractEntityFromStreamAndResetStream(
      Supplier<InputStream> inputStreamGetter,
      Consumer<InputStream> inputStreamSetter) throws IOException {
    ByteArrayOutputStream entityOutputStream = toOutputStream(inputStreamGetter.get());
    inputStreamSetter.accept(new ByteArrayInputStream(entityOutputStream.toByteArray()));
    String entityString = entityOutputStream.toString(Charset.defaultCharset());
    if (!entityString.isBlank()) {
      return entityString;
    }
    return Collections.emptyList();
  }

  private ByteArrayOutputStream toOutputStream(InputStream inputStream) throws IOException {
    ByteArrayOutputStream replicaOutputStream = new ByteArrayOutputStream();
    while ((inputStream.available() > 0)) {
      replicaOutputStream.writeBytes(inputStream.readNBytes(StaticConfig.CHUNK_SIZE_TO_READ));
    }
    return replicaOutputStream;
  }

  protected Map<String, List<String>> convertToMapStringListOfString(
      Map<String, List<Object>> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            entry -> entry.getValue().stream()
                .map(Object::toString)
                .collect(Collectors.toList())));
  }
}
