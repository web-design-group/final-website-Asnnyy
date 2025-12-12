            document.addEventListener('DOMContentLoaded', function() {
                const form = document.getElementById('dinnerForm');
                const registerBtn = document.getElementById('registerBtn');
                const nameInput = document.getElementById('nameInput');
                const guestsInput = document.getElementById('guestsInput');
                const phoneInput = document.getElementById('phoneInput');
                
                const modalName = document.getElementById('modalName');
                const modalGuests = document.getElementById('modalGuests');
                const modalImage = document.getElementById('modalImage');
                
                const modal = new bootstrap.Modal(document.getElementById('dinnerModal'));

                function validateName(name) {
                    const regex = /^[A-Za-zА-Яа-яЁё\s\-]+$/;
                    return regex.test(name);
                }

                function validatePhone(phone) {
                    const regex = /^[\+\d\s\-\(\)]+$/;
                    return regex.test(phone) && phone.replace(/\D/g, '').length >= 10;
                }

                function checkForm() {
                    const inputs = [nameInput, guestsInput, phoneInput];
                    let allValid = true;
                    let errorMessage = '';
                    
                    inputs.forEach(input => {
                        if (!input.value.trim()) {
                            allValid = false;
                            input.style.borderColor = 'red';
                        } else {
                            input.style.borderColor = '#918474';
                        }
                    });
                    
                    if (nameInput.value.trim() && !validateName(nameInput.value.trim())) {
                        allValid = false;
                        errorMessage = 'Имя может содержать только буквы, пробелы и дефисы';
                        nameInput.style.borderColor = 'red';
                    } else if (nameInput.value.trim()) {
                        nameInput.style.borderColor = '#918474';
                    }

                    if (phoneInput.value.trim() && !validatePhone(phoneInput.value.trim())) {
                        allValid = false;
                        errorMessage = 'Введите корректный номер телефона (минимум 10 цифр)';
                        phoneInput.style.borderColor = 'red';
                    } else if (phoneInput.value.trim()) {
                        phoneInput.style.borderColor = '#918474';
                    }

                    registerBtn.disabled = !allValid;
                    
                    if (allValid) {
                        registerBtn.setAttribute('data-bs-toggle', 'modal');
                        registerBtn.setAttribute('data-bs-target', '#dinnerModal');
                    } else {
                        registerBtn.removeAttribute('data-bs-toggle');
                        registerBtn.removeAttribute('data-bs-target');
                    }
                    
                    return allValid;
                }

                function fillModal() {
                    const name = nameInput.value.trim();
                    const guests = guestsInput.value.trim();
                    
                    if (name && guests) {
                        modalName.textContent = `${name}`;
                        modalGuests.textContent = guests;
                        modalImage.src = 'img/modal.png';
                        return true;
                    }
                    return false;
                }

                registerBtn.addEventListener('click', function(e) {
                    if (!checkForm()) {
                        e.preventDefault();
                        alert('Заполните все поля корректно');
                        return;
                    }
                    
                    fillModal();
                });
                
                form.addEventListener('input', checkForm);
                form.addEventListener('change', checkForm);

                checkForm();
                
                document.getElementById('dinnerModal').addEventListener('hidden.bs.modal', function() {
                    form.reset();
                    checkForm();
                });
            });