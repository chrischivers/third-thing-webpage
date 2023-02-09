package uk.co.thirdthing.content

object Copy:

  val porfolioItems = List(
    PortfolioItem(
      title = "Property History (2022)",
      description =
        """This project is a work in progress.
          |The intention is to provide a cohesive, historical view of the listing history for a particular property, going back many years.
          | Currently major property platforms (such as Rightmove) lack this functionality. A user can search for and view a current listing, but not view previous listings for the same property. They also are unable to see how the details for a particular listing have changed over time.
          | This information is valuable as it reveals more information about a particular property, empowering the consumer. It is also potentially useful for research purposes.
          |""".stripMargin,
      imageSrc = "simple-house-silhouette.jpg",
      githubUrl = "https://github.com/chrischivers/property-history",
      librariesAndFrameworks = List(
        "Scala 2.13",
        "Scala 3",
        "Scala.js",
        "Cats",
        "Cats Effect",
        "http4s",
        "Circe",
        "Newtypes",
        "Skunk",
        "Smithy4s",
        "Laminar"
      ),
      infrastructure = List("Postgres", "AWS", "RDS", "SQS", "ECS", "Fargate", "ALB", "CodePipeline", "DynamoDB (retired)")
    ),
    PortfolioItem(
      title = "This website (2023)",
      description =
        """This project is a work in progress.
      This is the source code for this website - built using Scala 3, Scala.js, Laminar. With Boostrap templating.
          |""".stripMargin,
      imageSrc = "website-icon.png",
      githubUrl = "https://github.com/chrischivers/third-thing-webpage",
      librariesAndFrameworks = List("Scala 3", "Scala.js", "Laminar", "Circe", "Cats"),
      infrastructure = List("AWS", "S3", "Route53")
    ),
    PortfolioItem(
      "Multi-city flight scraper (2020)",
      "Scraping websites for low-priced multi-city flight itineraries",
      "Plane-Taking-Off-Silhouette.png",
      "https://github.com/chrischivers/multi-city-flight-scraper",
      List("Scala 2.13", "Cats", "Cats Effect", "Circe", "Selenium"),
      List("AWS", "DynamoDB")
    ),
    PortfolioItem(
      "Master Archer Automation (2019)",
      "Automation engine to play the web game Master Archer, using primitive ML to improve performance",
      "bow-and-arrow-2.png",
      "https://github.com/chrischivers/master-archer-automation",
      List("Scala 2.12", "Cats", "Cats Effect", "Circe", "Selenium", "Doobie"),
      List("Postgres")
    )
  )

  /*
    val baseElement    = dom.document.getElementById("page-top")
    val portfolioItems = List(
      PortfolioItem("Multi-city flight scraper", "Somethign here", "Plane-Taking-Off-Silhouette.png", "https://github.com/chrischivers/multi-city-flight-scraper"),
      PortfolioItem("Master archer automation", "Somethign here", "bow-and-arrow-2.png", "https://github.com/chrischivers/master-archer-automation")

   */