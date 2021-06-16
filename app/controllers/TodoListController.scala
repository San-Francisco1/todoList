package controllers

import actions.AuthRefiner
import play.api.mvc.InjectedController
import views.html.index

import javax.inject.Inject

class TodoListController @Inject()(
  auth: AuthRefiner,
)(
  indexView: index
) extends InjectedController {
  def getIndexView = auth { request =>
    Ok(indexView(request.user))
  }


}
