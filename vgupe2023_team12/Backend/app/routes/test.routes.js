module.exports = app => {
    const website = require("../controllers/test.controller");
  
    var router = require("express").Router();

    // Get Total Fine by Member Id
    router.get("/getTotalFine/:id",website.getFindById);

    router.get("/getBAA/",website.findAllBAA);

    app.use('/api/test', router);
  };