/**
 * Created by vinuthna on 01-02-2017.
 */



var module = angular.module("myApp",[]);
module.controller("MainController",Main);
module.controller("LoginController",Login);
module.controller("RegisterController",Register);

function Main($scope,$window) {

    $scope.loginBtn = function () {
        $window.location.href = 'login.html';
    }
    $scope.registerBtn = function () {
        $window.location.href = 'register.html';
    }
}

function Login($scope,$window){

    $scope.login = function () {
        $window.location.href = 'home.html';
    }
}

function Register($scope,$window){

    $scope.register = function () {
        localStorage.setItem($scope.email , $scope.password);
        $window.location.href = 'index.html';
    }
}

