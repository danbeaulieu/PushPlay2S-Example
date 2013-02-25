package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json.Json._

import models.pushplay2s._
import java.util.UUID

object Application extends Controller {
  
  def index = Action {
    val port = "9000"
    Ok(views.html.index(port))
  }

  def auth = Action { implicit request => 
    val socket_id: String = request.body.asFormUrlEncoded match {
      case Some(m) => m.get("socket_id").get(0)
      case None => "" //TODO do something better here
    }
    val channel: String = request.body.asFormUrlEncoded match {
      case Some(m) => m.get("channel_name").get(0)
      case None => "" //TODO do something better here
    }
    val channelData: Option[String] = channel match {
      case cd if (cd.startsWith("presence-")) => {
        // Just make something up
        Some(Json.stringify(Json.toJson(
          Map("user_id" -> toJson(UUID.randomUUID.toString), 
              "user_info" -> toJson(
                Map("name" -> UUID.randomUUID.toString.substring(0, 8))
          )))))
      }
      case _ => None    
    }
	  val token: String = Authenticator.computeSignature(channel, socket_id, channelData);
	  Ok(Json.toJson(
      Map("auth" -> (Play.current.configuration.getString("pusher.app_key").get + ":" + token),
          "channel_data" -> channelData.map{cd => cd}.getOrElse(""))
    ))
  }
  
}
