// Контроллер основного приложения
app.controller('RefCorrespondentsAppController', function($http, $location, $uibModal) {

    const demoApp = this;

    // We identify the node.
    const apiBaseURL = "/api/example/";



    demoApp.openOffer = (item) => {
        const modalInstance = $uibModal.open({
            templateUrl: 'Offer.html',
            controller: 'CreateOrEditItemCtrl',
            controllerAs: 'modalInstance',
            resolve: {
                item: () => item,
                demoApp: () =>  demoApp,
                apiBaseURL: () => apiBaseURL
            }
        });

        modalInstance.result.then(() => {}, () => {});
    };
   
     
     demoApp.openModal = (item) => {
        const modalInstance = $uibModal.open({
            templateUrl: 'createOrEditItemTemplate.html',
            controller: 'CreateOrEditItemCtrl',
            controllerAs: 'modalInstance',
            resolve: {
                item: () => item,
                demoApp: () => demoApp,
                apiBaseURL: () => apiBaseURL
            }
        });

        modalInstance.result.then(() => {}, () => {});
    };


// Отобразить сообщение
    demoApp.displayMessage = (message) => {
        const modalInstanceTwo = $uibModal.open({
            templateUrl: 'messageContent.html',
            controller: 'messageCtrl',
            controllerAs: 'modalInstanceTwo',
            resolve: { message: () => message }
        });

        // Поведение на закрытии модального окна отсутствует
        modalInstanceTwo.result.then(() => {}, () => {});
    };



/*demoApp.getOffer = () => $http.get(apiBaseURL + "get-offer")
       .then((response) =>
      demoApp.offers = Object.keys(response.data)
                           .map((key) =>
                           {
                               var offer = response.data[key];
                               if(offer.oferrCreate){
                                  offer.oferrCreate = new Date(offer.oferrCreate).toLocaleDateString("ru-Ru");
                               }
                               return offer;
                           })
           .reverse());

  demoApp.getOffer();

demoApp.getAllItems = () => $http.get(apiBaseURL + "get-offer")
       .then((response) =>
       demoApp.items = Object.keys(response.data)
                           .map((key) =>
                           {
                               var item= response.data[key];
                               if(item.itemCreate){
                                   item.itemCreate = new Date(item.itemCreate).toLocaleDateString("ru-Ru");
                               }
                               return item;
                           })
           .reverse());

    demoApp.getAllItems();
*/
demoApp.getAllItems = () => $http.get(apiBaseURL + "get-user")
       .then((response) =>
       demoApp.items = Object.keys(response.data)
                           .map((key) =>
                           {
                               var item= response.data[key];
                               if(item.offerCreate){
                                   item.itemCreate = new Date(item.itemCreate).toLocaleDateString("ru-Ru");
                               }
                               return item;
                           })
           .reverse());

    demoApp.getAllItems();


demoApp.getAllOffer = () => $http.get(apiBaseURL + "get-offer")
       .then((response) =>
       demoApp.offers = Object.keys(response.data)
                           .map((key) =>
                           {
                               var offer= response.data[key];
                               if(offer.offerCreate){
                                   offer.offerCreate = new Date(offer.offerCreate).toLocaleDateString("ru-Ru");
                               }
                               return offer;
                           })
           .reverse());

    demoApp.getAllOffer();


    }); 


// Контроллер для модельного окна создания/редактирования записи
app.controller('CreateOrEditItemCtrl', function ($http, $location, $uibModalInstance, $uibModal, item, demoApp, apiBaseURL) {
    const modalInstance = this;

    modalInstance.form = {};
    modalInstance.formError = false;
    modalInstance.isEditMode = false; // false - create mode

    if(item){ 
        
        //TODO: Заполнение формы при редактировании
        modalInstance.form.cor_id = item.cor_id;
        modalInstance.form.cor_name = item.cor_name;
        modalInstance.form.phone = item.phone;
        modalInstance.form.cor_bill_1 = item.cor_bill_1;
        modalInstance.form.cor_bill_2 = item.cor_bill_2;
        modalInstance.form.city = item.city;

        modalInstance.isEditMode = true;
    } else {
        modalInstance.form.id = "1";
        modalInstance.form.name = "Name";
        modalInstance.form.phone = "89";
        modalInstance.form.cor_bill_1 = "0";
        modalInstance.form.cor_bill_2 = "0"
        modalInstance.form.citty = "City"

         modalInstance.form.id ="1";
          modalInstance.form.name ="Company";
    }

    modalInstance.title = modalInstance.isEditMode ? "Редактировать элемент" : "Новый элемент";

    modalInstance.create = () => {
        if (invalidFormInput()) {
            modalInstance.formError = true;
        } else {
            modalInstance.formError = false;

            $uibModalInstance.close();
            
            const createCorrespondentEndpoint = `${apiBaseURL}create-user?cor_id=${modalInstance.form.cor_id}&cor_name=${modalInstance.form.cor_name}&phone=${modalInstance.form.phone}&cor_bill_1=${modalInstance.form.cor_bill_1}&cor_bill_2=${modalInstance.form.cor_bill_2}&city=${modalInstance.form.city}`;

            $http.put(createCorrespondentEndpoint).then(
                (result) => {
                    modalInstance.displayMessage(result);
                    demoApp.getAllCorrespondents();
                },
                (result) => {
                    result.data = "Ошибка создания транзакции: " + result.status +' '+ result.statusText + " [" + result.data +"]";
                    modalInstance.displayMessage(result);
                }
            );
        
        }
    };



    modalInstance.createOfert = () => {
        if (invalidFormInputOfert()) {
            modalInstance.formError = true;
        } else {
            modalInstance.formError = false;

            $uibModalInstance.close();
            
            const createCorrespondentEndpoint = `${apiBaseURL}create-offer?id=${modalInstance.form.id}&phone=${modalInstance.form.phone}&city=${modalInstance.form.city}&name=${modalInstance.form.name}`;

            $http.put(createCorrespondentEndpoint).then(
                (result) => {
                    modalInstance.displayMessage(result);
                    demoApp.getAllCorrespondents();
                },
                (result) => {
                    result.data = "Ошибка создания транзакции: " + result.status +' '+ result.statusText + " [" + result.data +"]";
                    modalInstance.displayMessage(result);
                }
            );
           
        }
    };




    modalInstance.save = () => {

        if (invalidFormInput()) {
            modalInstance.formError = true;
        } else {
            modalInstance.formError = false;

       const changeCorrespondentEndpoint = `${apiBaseURL}change-user`;
        var newItem = item;
        if(newItem){
            newItem.cor_id = modalInstance.form.cor_id;
            newItem.cor_name = modalInstance.form.cor_name;
            newItem.phone = modalInstance.form.phone;
            newItem.cor_bill_1 = modalInstance.form.cor_bill_1;
            newItem.cor_bill_2 = modalInstance.form.cor_bill_2;
            newItem.city = modalInstance.form.city;
        } 




         $http.put(changeCorrespondentEndpoint, newItem).then(
            (result) => {
                modalInstance.displayMessage(result);
                demoApp.getAllItems();
                $uibModalInstance.close();
            },
            (result) => {
                result.data = "Ошибка сохранения транзакции: " + result.status +' '+ result.statusText + " [" + result.data +"]";
                modalInstance.displayMessage(result);
            }
        );
   
        }
}


    modalInstance.displayMessage = (message) => {
        const modalInstanceTwo = $uibModal.open({
            templateUrl: 'messageContent.html',
            controller: 'messageCtrl',
            controllerAs: 'modalInstanceTwo',
            resolve: { message: () => message }
        });

 
        modalInstanceTwo.result.then(() => {}, () => {});
    };


    modalInstance.cancel = () => $uibModalInstance.dismiss();


    function invalidFormInput() {
        var invalid = IsNullOrWhitespace(modalInstance.form.cor_id) || 
        IsNullOrWhitespace(modalInstance.form.cor_name);

        return invalid;
    }

    function invalidFormInputOfert() {
        var invalid = IsNullOrWhitespace(modalInstance.form.id)

        return invalid;
    }
});    