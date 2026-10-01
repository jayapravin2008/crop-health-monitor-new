
document.addEventListener("DOMContentLoaded", function() {
    const imageInput = document.getElementById('imageInput');
    const uploadPlaceholder = document.getElementById('uploadPlaceholder');
    const imagePreviewContainer = document.getElementById('imagePreviewContainer');
    const imagePreview = document.getElementById('imagePreview');
    const fileNameDisplay = document.getElementById('fileName');
    const analyzeForm = document.getElementById('analyzeForm');
    const analyzeBtn = document.getElementById('analyzeBtn');
    const loadingSpinner = document.getElementById('loadingSpinner');

    if(imageInput) {
        imageInput.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                const file = this.files[0];
                const validTypes = ['image/jpeg', 'image/jpg', 'image/png'];
                
                if (!validTypes.includes(file.type)) {
                    alert('Please select a valid JPG or PNG image.');
                    this.value = '';
                    return;
                }
                
                if (file.size > 10 * 1024 * 1024) {
                    alert('Image size exceeds 10MB limit.');
                    this.value = '';
                    return;
                }

                const reader = new FileReader();
                reader.onload = function(e) {
                    imagePreview.src = e.target.result;
                    fileNameDisplay.textContent = file.name;
                    uploadPlaceholder.classList.add('d-none');
                    imagePreviewContainer.classList.remove('d-none');
                }
                reader.readAsDataURL(file);
            } else {
                uploadPlaceholder.classList.remove('d-none');
                imagePreviewContainer.classList.add('d-none');
                imagePreview.src = '';
            }
        });
    }

    if(analyzeForm) {
        analyzeForm.addEventListener('submit', function() {
            analyzeBtn.classList.add('d-none');
            loadingSpinner.classList.remove('d-none');
        });
    }
});
