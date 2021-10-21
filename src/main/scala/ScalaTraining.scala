import java.util.Properties


object ScalaTraining {

  def main(args: Array[String]) : Unit = {

    val mavar : String = "hello world"
    println(mavar)

    val mavar1 : String = mavar + "hello world"

    val test  = 1

    // mavar1 = mavar + "test de la variaable"
    extractFirst("extract first" + mavar, 3)
    val newText : String = extractFirstFonction(mavar, 3)

    println(newText)

    var i = 0

    while(i < 10) {
      println(s"voici la valeur de ${i}")
      i = i + 1
    }

    for( i <- 1 to 10) {
      println(s"voici la valeur de ${i}")
    }

    val maListe  : List[String] = List("juvenal", "taylor", "adrien", "bruno")
    maListe.foreach{e => println(s"élement de la liste : ${e}")}

    maListe.foreach{
      e => {
      val t =  e.substring(1, 2)
      val result = "15" + t
      println(result)
        }
    }

    val maListe2 = maListe.map(m => m.substring(1,2))   // transformation
    val maListe3 = maListe2.filter(f => f == "a")
    maListe3.foreach{ e => println(s"filtre : ${e}")}

    maListe2.foreach{                                  // action
      e => println(e)
    }

    val intList : List[Int] = List(10, 48, 89, 100, 46)
    val intList2 = intList.map(_*3)
    intList2.foreach{ e => println(e)}


    val t = maListe(0)   // premier de la liste

    val tp : (String, Int, Boolean, String)= ("juvenal", 10, true, "adrien")
    val texte = tp._4

    val map1 : Map[String, String] = Map("adresseIP" -> "localhost", "hostName" -> "Juvenal", "portNumber" -> "3392")
    val map2 : Map[String, Int] = Map("distance" -> 100, "distance" -> 250, "distance" -> 350)
    val map3 : Map[String, List[String]] = Map("villes" -> List("Paris", "Tokyo", "New York"), "pays" -> List("France", "Japon", "USA"))

    map1.keys.foreach{ k => println(s"clés de mon map1 : ${k}")}
    val cle1 = map1("adresseIP")
    println(cle1)
    map2.values.foreach{ k => println(s"clés de mon map1 : ${k}")}

    map3.values.foreach{ l => {
      l.foreach{ e => println(s"valeur de mon map3 : ${e}")}
       }
    }

    val monTableau : Array[String] = Array("juvenal", "boubdallah", "bruno", "vincent", "taylor")
    monTableau(0)
    val listeTableau = monTableau.toList

    val maseq : Seq[String] = Seq("juvenal", "boubdallah", "bruno", "vincent", "taylor")




  }

  def extractFirst(texte : String, longueur : Int) : Unit = {
    val resultat =  texte.substring(longueur)
    resultat
  }

  def extractFirstFonction(texte : String, longueur : Int) : String = {
    val resultat =  texte.substring(longueur)
   return resultat
  }


}



