// Dependencies
var express     = require('express');
var multer      = require('multer');
var crypto      = require('crypto');
var mime        = require('mime');
var im          = require('imagemagick');

// Models
var Post        = require('../models/post');
var User        = require('../models/user');

var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'public/uploads/posts')
  },
  filename: function (req, file, cb) {
    crypto.pseudoRandomBytes(16, function (err, raw) {
      cb(null, raw.toString('hex') + Date.now() + '.' + mime.extension(file.mimetype));
    });
  }
});

var upload      = multer({storage: storage});
var router      = express.Router();

var distance = 5/6378100;

// Routes
router.route('/all').get(function(req, res, next){
    var last_id = req.query.lastid;
    if(last_id == undefined) {
        Post
            .find({'location': {
		$geoWithin: {
		    $centerSphere: [[    
			req.query.lng,
		   	req.query.lat
		    ], distance
		    ]
	    	}
	    }})
            //.limit(5)
            .sort({_id: -1})
	    .limit(10)
	    .populate('user')
            .exec(function(error, result){
                if(error) return next(error);
                //res.header('Cache-Control', 'public, max-age=31557600'); //One year
                res.json(result);
        });
    } else {
        Post
	    .find({'location': {
                $geoWithin: {
                    $centerSphere: [[
                        req.query.lng,
                        req.query.lat
                    ], distance
                    ]
                }
            }, _id :{"$lt": last_id}
	    })
            .limit(10)
            .sort({_id: -1})
	    //.limit(10)
	    .populate('user')
            .exec(function(error, result){
                if(error) return next(error);
                //res.header('Cache-Control', 'public, max-age=31557600'); //One year
                res.json(result);
        });
    }
});

router.route('/all/:username').get(function(req, res, next){
    var last_id = req.query.lastid;
    if(last_id == undefined) {
        Post
            .find({'userName': req.params.username})
            .limit(10)
            .sort({_id: -1})
            .exec(function(error, result){
                if(error) return next(error);
                //res.header('Cache-Control', 'public, max-age=31557600'); //One year
                res.json(result);
        });
    } else {
        Post
            .find({'userName': req.params.username, _id :{"$lt": last_id}})
            .limit(10)
            .sort({_id: -1})
            .exec(function(error, result){
                if(error) return next(error);
                //res.header('Cache-Control', 'public, max-age=31557600'); //One year
                res.json(result);
        });
    }
});

router.route('/top').get(function(req, res, next){
    var last_id = req.query.lastid;
    if(last_id == undefined) {
        Post
            .find()
            .sort('-likesCount')
            .limit(10)
            .exec(function(error, result){
                if(error) return next(error);
                //res.header('Cache-Control', 'public, max-age=31557600'); //One year
                res.json(result);
        });
    } else {
        Post
            .find()
            .sort('-likesCount')
            .skip(last_id * 10)
            .limit(10)
            .exec(function(error, result){
                if(error) return next(error);
                //res.header('Cache-Control', 'public, max-age=31557600'); //One year
                res.json(result);
        });
    }
});

router.route('/add').post(upload.array('photos', 6), function(req, res, next){
    console.log(req.body.token);
    var post = new Post(req.body);
    //var lat = parseFloat(req.body.lat);
    //var lng = parseFloat(req.body.lng);
    //console.log("lat: "+ lat + " long: " + lng);
    post.location = {type: "Point", coordinates: [ req.body.lng, req.body.lat ]};
    var dir  = ('/home/ubuntu/yellow/public/uploads/posts/');
    var imgs = [];
    for(var key in req.files){
        /*im.resize({
            srcPath: dir + req.files[key].filename,
            dstPath: dir + 'compressed/' + req.files[key].filename,
            quality: 0.6,
            width: ""}, function(err, stdout){
                if (err) return next(err);

                console.log("Image compressed");
        });*/
        imgs.push("http://ec2-52-11-84-14.us-west-2.compute.amazonaws.com:3000/static/uploads/posts/"+req.files[key].filename);
        console.log(req.files[key].filename);
    }
    post.imageUrl = imgs;

    post.validate(function(error){
        post.save(function(error, result){
            if(error) return next(error);
            res.json(result);
        });
    });
});

router.route('/:post_id').put(function(req, res, next){
    Post.findOne({_id: req.params.post_id}, function(error, post){
        if(error) return next(error);
        post.set(req.body);
        post.save(function(error){
            if(error) return next(error);
            res.json({message: 'Post updated'});
        });
    });
});

router.route('/:post_id').delete(function(req, res, next){
    Post.findOne({_id: req.params.post_id}, function(error, post){
        if(error) return next(error);
        post.remove(function(error){
            if(error) return next(error);
            res.json({message: 'Post deleted'});
        });
    });
});

router.route('/like').post(function(req, res, next){
    Post.findOne({_id: req.body.post_id}, function(error, post){
        if(error) return next(error);
        var userId = req.body.userId;

        var index = post.likes.indexOf(userId);
        if(index == -1) {
            post.likes.push(userId);
            post.likesCount++;
            post.save(function(error){
                if(error) return next(error);
                res.json({message: 'Post liked'});
            });
        } else {
            post.likes.splice(index, 1);
            post.likesCount--;
            post.save(function(error){
                if(error) return next(error);
                res.json({message: 'Post unliked'});
            });
        }
    });
});

// Return router
module.exports = router;
