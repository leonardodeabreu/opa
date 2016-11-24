module.exports = function(grunt) {
  require('jit-grunt')(grunt);

  var lessPath = '../',
      filesObject = {};
      fileNames = ['main','core'];

  for (var i = fileNames.length - 1; i >= 0; i--) {
    filesObject[ lessPath+'compiled/'+ fileNames[i] +'.css' ] = lessPath+ fileNames[i] +'.less';
  };

  grunt.initConfig({
    less: {
      development: {
        options: {
          compress: true,
          yuicompress: true,
          optimization: 2,
          plugins: [
            new (require('less-plugin-autoprefix'))({browsers: ["last 4 versions"]}),
            new (require('less-plugin-clean-css'))({advanced: true,keepSpecialComments: 0})
          ],
          modifyVars: {
            themeurl: '"../../../"',
            lessurl: '"../../"'
          }
        },
        files: filesObject
      }
    },
    watch: {
      styles: {
        files: [ lessPath+'*.less', lessPath+'*/*.less' ], // which files to watch
        tasks: ['less'],
        options: {
          nospawn: true
        }
      }
    }
  });

  grunt.registerTask('default', ['less', 'watch']);
};