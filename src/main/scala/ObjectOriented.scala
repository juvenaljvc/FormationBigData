import ScalaTraining._

object ObjectOriented {

  def main(args: Array[String]) : Unit = {
    val facture1 = SchemaFacture("BK34", "20/10/2021", "Juvenal", 3500)

    val result = ScalaTraining.extractFirstFonction(facture1.factureid, 1)

    val commande1 = DetailFacture(facture1, "ki897C", 1, 800)
    val commande2 = DetailFacture(SchemaFacture("BK35", "21/10/2021", "Julio", 2500), "ki897d", 2, 2220)

    commande2.idfacture.factureid
    println(result)

    val commande3 = Commande(facture1, commande1)
    val revenueFacture1 = commande3.revenueCompute(commande3.produitCommande.quantite, commande3.produitCommande.prixUnitaire)

    println(s"le total de ma facture est de ${revenueFacture1}")


  }

}
