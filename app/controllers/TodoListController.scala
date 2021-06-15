package controllers

import javax.inject.Inject

//import actions.AuthRefiner
import play.api.mvc.InjectedController
import views.html.index

class TodoListController @Inject()(
//  auth: AuthRefiner
)(
  indexView: index
) extends InjectedController {
  def getIndexView = Action {
    Ok(indexView())
  }
}
