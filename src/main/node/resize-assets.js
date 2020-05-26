const glob = require('glob');

const Jimp = require('jimp');

function resize(image) {
  let fileName = image.split('/').pop().split('.')[0],
    newImage = `./src/assets/cards/${fileName}-sm.jpg`;
  Jimp.read(image, (err, jimpImage) => {
    if (err) throw err;
    jimpImage
      .resize(94, 150) // resize
      .quality(100) // set JPEG quality
      .write(newImage); // save
  });

  return newImage;
}


function findPngs() {
  return new Promise((resolve)=> {
    glob("./src/assets/**/*.jpg", function (er, files) {
      resolve(files)
    });
  });
}


findPngs()
  .then( (files)=> files.map(resize) );
