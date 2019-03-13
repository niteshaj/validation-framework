package org.nj.validator.response;

import lombok.extern.slf4j.Slf4j;
import org.nj.validator.enums.error.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import javax.inject.Named;
import java.text.MessageFormat;

@Slf4j
@Named
public class LocalizationHelper {
  private static MessageSource messageSource;

  @Autowired
  LocalizationHelper(MessageSource messageSource) {
    LocalizationHelper.messageSource = messageSource;
  }

  static String getLocalizedMessage(ErrorCode errorCode, Object... params) {
    String message;
    try {
      message = messageSource.getMessage(errorCode.getCode(), params, LocaleContextHolder.getLocale());
    } catch (NoSuchMessageException e) {
      log.error("No message found for {} in resource bundle...", errorCode.getCode());
      message = MessageFormat.format(errorCode.getDescription(), params);
    }
    return message;
  }
}
