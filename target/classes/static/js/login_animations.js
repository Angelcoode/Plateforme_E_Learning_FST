// ======================================================
// 🔐 LOGIN + REGISTER ANIMATIONS - FULL VERSION
// ======================================================

document.addEventListener("DOMContentLoaded", () => {

    // ==================================================
    // 👁️ PASSWORD TOGGLE (LOGIN + REGISTER)
    // ==================================================
    document.querySelectorAll('.toggle-password').forEach(btn => {
        btn.addEventListener('click', () => {

            const wrapper = btn.closest('.password-wrapper');
            const input = wrapper?.querySelector('input');
            const icon = btn.querySelector('i');

            if (!input) return;

            if (input.type === "password") {
                input.type = "text";
                icon.classList.remove('bi-eye');
                icon.classList.add('bi-eye-slash');
            } else {
                input.type = "password";
                icon.classList.remove('bi-eye-slash');
                icon.classList.add('bi-eye');
            }
        });
    });

    // ==================================================
    // ❌ CLOSE ALERTS
    // ==================================================
    document.querySelectorAll('.btn-close-alert').forEach(btn => {
        btn.addEventListener('click', () => {
            const alert = btn.closest('.custom-alert');
            if (!alert) return;

            alert.classList.add('fade-out');

            setTimeout(() => {
                alert.remove();
            }, 300);
        });
    });

    // ==================================================
    // 🎯 INPUT FOCUS ANIMATION
    // ==================================================
    document.querySelectorAll('.form-control').forEach(input => {

        input.addEventListener('focus', () => {
            input.parentElement.classList.add('focused');
        });

        input.addEventListener('blur', () => {
            input.parentElement.classList.remove('focused');
        });
    });

    // ==================================================
    // 🔥 REGISTER PASSWORD MATCH VALIDATION
    // ==================================================
    const password = document.getElementById("password");
    const confirm = document.getElementById("confirmPassword");

    if (password && confirm) {

        function validatePasswords() {
            if (confirm.value === "") return;

            if (password.value !== confirm.value) {
                confirm.style.borderColor = "#ef4444";
                confirm.setCustomValidity("Passwords do not match");
            } else {
                confirm.style.borderColor = "#10b981";
                confirm.setCustomValidity("");
            }
        }

        password.addEventListener("input", validatePasswords);
        confirm.addEventListener("input", validatePasswords);
    }

    // ==================================================
    // 🎯 FORM SUBMIT LOADING EFFECT
    // ==================================================
    document.querySelectorAll("form").forEach(form => {

        form.addEventListener("submit", (e) => {

            const btn = form.querySelector(".submit-btn");

            if (!btn) return;

            btn.disabled = true;
            btn.innerHTML = `
                <span>Chargement...</span>
                <i class="bi bi-arrow-repeat spinner"></i>
            `;

        });

    });

});

// ======================================================
// 🎯 DEMO ACCOUNT FILL (LOGIN PAGE)
// ======================================================
function fillDemo(email, password) {

    const user = document.getElementById('username');
    const pass = document.getElementById('password');

    if (user && pass) {

        user.value = email;
        pass.value = password;

        user.classList.add('input-fill');
        pass.classList.add('input-fill');

        setTimeout(() => {
            user.classList.remove('input-fill');
            pass.classList.remove('input-fill');
        }, 800);
    }
}