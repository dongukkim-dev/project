// 로그인 기능
const signUpButton = document.getElementById('signup-btn');

if (signUpButton) {
    // 등록 버튼을 클릭하면 /login 로 요청을 보낸다
    signUpButton.addEventListener('click', event => {
        function success() {
            alert('회원가입에 성공했습니다.');
            location.replace('/login');
        };
        function fail() {
            alert('회원가입에 실패했습니다.');
            location.replace('/signup');
        };

        fetch("/api/users", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: document.getElementById('email').value,
                password: document.getElementById('password').value
            }),
        }).then(response => {
            if (response.status === 200 || response.status === 201) {
                return success();
            } else {
                return fail();
            }
        });
    });
}