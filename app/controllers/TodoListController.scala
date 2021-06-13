package controllers

import javax.inject.Inject

import play.api.mvc.InjectedController

class TodoListController @Inject()() extends InjectedController {
  def indexView = Action {
    Ok("Index Page")
  }
}
