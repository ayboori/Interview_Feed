function check_username() {
    var username = document.getElementById('username').value;

    // 이메일 정규식
    let regexp_username = new RegExp('^(?=.*[a-z0-9]).{4,10}$')

    if (!regexp_username.test(username)) {
        document.getElementById('check_regexp_username').innerHTML = '4~10자 알파벳 소문자(a~z) 및 숫자(0~9)를 사용하세요.';
        document.getElementById('check_regexp_username').style.color = 'red';
        return false;
    } else {
        document.getElementById('check_regexp_username').innerHTML = '유효한 아이디 형식 입니다.';
        document.getElementById('check_regexp_username').style.color = 'blue';
        return true;
    }
}

function confirm_username() {
    if (!check_username()) {
        alert('유효하지 않은 아이디 형식 입니다.');
        return false;
    }

    const username = document.getElementById('username').value;

    $.ajax({
        type: 'POST',
        url: '/api/signup/confirm-username/' + encodeURIComponent(username),
        contentType: "application/json",
        // data: JSON.stringify(username)
    }).done(function (data, textStatus, xhr) {
        if (data !== '') {
            alert('사용할 수 없는 아이디 입니다.');
            return;
        }
        alert('사용할 수 있는 아이디 입니다.');
    })
        .fail(function (xhr, textStatus, errorThrown) {
            alert(errorThrown);
        });
}

function check_password() { // 비밀번호 일치 및 정규식 체크
    var p1 = document.getElementById('pw1').value;
    var p2 = document.getElementById('pw2').value;

    // 비밀번호 정규식
    let regexp_pw = new RegExp('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^*+=-]).{8,15}$')

    if (!regexp_pw.test(p1)) { // 정규식 검사
        document.getElementById('check_regexp_pw').innerHTML = '8~15자 영문 대문자(A~Z), 소문자(a~z), 숫자(0~9), 특수문자(!@#$%^*+=-)를 사용하세요.';
        document.getElementById('check_regexp_pw').style.color = 'red';
        return false;
    } else {
        document.getElementById('check_regexp_pw').innerHTML = '사용 가능한 비밀번호 입니다.';
        document.getElementById('check_regexp_pw').style.color = 'blue';

        if (p1 != p2) { // 비밀번호 일치 검사
            document.getElementById('check_accord_pw').innerHTML = '비밀번호가 일치하지 않습니다.';
            document.getElementById('check_accord_pw').style.color = 'red';
            return false;
        } else {
            document.getElementById('check_accord_pw').innerHTML = '비밀번호가 일치합니다.'
            document.getElementById('check_accord_pw').style.color = 'blue';
            return true;
        }
    }
}

function check_email() { // 이메일 정규식 체크
    var email = document.getElementById('email').value;

    // 이메일 정규식
    let regexp_email = new RegExp('^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$')

    if (!regexp_email.test(email)) {
        document.getElementById('check_regexp_email').innerHTML = '유효하지 않은 이메일 형식 입니다.';
        document.getElementById('check_regexp_email').style.color = 'red';
        return false;
    } else {
        document.getElementById('check_regexp_email').innerHTML = '유효한 이메일 형식 입니다.';
        document.getElementById('check_regexp_email').style.color = 'blue';
        return true;
    }
}

var send_code = false;

function confirm_email() {
    if (!check_email()) {
        alert('유효하지 않은 이메일 형식 입니다.');
        return false;
    }

    const email = document.getElementById('email').value;

    $.ajax({
        type: 'POST',
        url: '/api/signup/confirm-email/' + encodeURIComponent(email),
        contentType: "application/json",
        // data: JSON.stringify(email)
    }).done(function (data, textStatus, xhr) {
        if (data !== '') {
            alert('사용할 수 없는 이메일 입니다.');
            return;
        }
    })
        .fail(function (xhr, textStatus, errorThrown) {
            alert(errorThrown)
            return;
        });


    // ***** 중복 검사를 통과하면 메일을 보낸다
    $.ajax({
        type: 'POST',
        url: '/api/email/send-email',
        contentType: "application/json",
        data: JSON.stringify(email)
    }).done(function (data, textStatus, xhr) {
        alert('메일에 적힌 인증코드를 입력해주세요.');

        // **** 인증 코드 입력 창을 연다
        var box = document.getElementById("confirm_email_box");
        box.style.display = "block";

        return;
    })
        .fail(function (xhr, textStatus, errorThrown) {
            alert('메일 전송에 실패했습니다.' + errorThrown);
            return;
        });
}

function confirm_authcode() {

    const input = document.getElementById("confirm_email_code").value;

    // ***** 중복 검사를 통과하면 메일을 보낸다
    $.ajax({
        type: 'POST',
        url: '/api/email/confirm-authcode',
        contentType: "application/json",
        data: input
    }).done(function (data, textStatus, xhr) {
        if (data !== '') {
            alert('유효한 코드가 아닙니다');
            return;
        }
        alert('이메일 인증이 완료되었습니다.');
    })
        .fail(function (xhr, textStatus, errorThrown) {
            alert('errorThrown');
        });
}