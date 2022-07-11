paymentForm()

function paymentForm() {

    document.getElementById("formPay").insertAdjacentHTML("beforeend",
        `
            <div class="container-md">
                <div class="col-6">
                  <form>
                      <div class="form-group mb-3" >
                        <label for="payment-summ">Сумма платежа</label>
                        <input type="number" class="form-control" id="payment-summ" required aria-describedby="emailHelp" placeholder="123 456" >
                      </div>
                      <div class="form-group mb-3" >
                        <label for="payment-account-number">Номер рассчетного счета</label>
                        <input type="number" class="form-control" id="payment-account-number" aria-describedby="emailHelp" placeholder="0000 0000 0000 0000" required>
                      </div>
                      <div class="form-group mb-3">
                        <label for="payment-date">Дата</label>
                        <input type="date"  id="payment-date" class="form-control" required>
                      </div>
                       <div class="form-group mb-3" >
                        <label for="payment-message">Комментарий</label>
                        <input type="textarea" class="form-control" id="payment-message" aria-describedby="emailHelp" placeholder="Что-то важное" required>
                      </div>
                      
                      <button type="submit" class="btn btn-primary" onclick=sendPayRequest()>Отправить</button>
                    </form>
                </div>
            </div>
           `
    )
}

function sendPayRequest() {
    let paymentReport = {};
    paymentReport.sum = $("#payment-summ").val();
    paymentReport.accountNumber = $("#payment-account-number").val();
    paymentReport.date = $("#payment-date").val();
    paymentReport.comment = $("#payment-message").val();

    console.log(paymentReport);
    if (paymentReport.sum === "" || paymentReport.date == "" || paymentReport.accountNumber == "" || paymentReport.comment == "") {
        alert("Заполните все поля")
    } else {
        let confirmation =
            confirm("Оптравить отчет о перводе " + paymentReport.sum + " рублей на счет " + paymentReport.accountNumber + " за дату " + paymentReport.date + "?");
        if (confirmation === true) {
            $.ajax({
                method: 'POST',
                url: "/payment",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(paymentReport),
                success: function (result) {
                },
                error: function (error) {
                    console.log(error);
                }
            });
            alert("Отчет отправлен!");
        } else {
            alert("Отчет не отправлен!");
        }
    }


}