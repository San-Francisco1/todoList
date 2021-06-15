package utils

import javax.inject.Inject

import play.api.http.DefaultHttpFilters

class HttpFilters @Inject() (
  loggingFilter: LoggingFilter
) extends DefaultHttpFilters(loggingFilter)
