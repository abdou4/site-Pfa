document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('img');
    
    images.forEach(function(img) {
        img.addEventListener('error', function() {
            this.classList.add('missing-image');
            
            let context = 'movie';
            if (this.closest('.movie-card') || this.closest('.card')) {
                context = this.alt ? this.alt.toLowerCase() : 'contenu';
                if (context.includes('série') || context.includes('series')) {
                    context = 'série';
                } else if (context.includes('épisode') || context.includes('episode')) {
                    context = 'épisode';
                }
            }
            
            this.setAttribute('alt', 'Image non disponible');
            
            const card = this.closest('.card-body');
            if (card) {
                const placeholder = document.createElement('div');
                placeholder.className = 'missing-image-placeholder';
                placeholder.innerHTML = `<i class="fas fa-film"></i><p>${context}</p>`;
                this.parentNode.replaceChild(placeholder, this);
            }
        });
    });
});

function handleMissingImages() {
    const images = document.querySelectorAll('img:not(.handled)');
    
    images.forEach(function(img) {
        img.classList.add('handled');
        
        if (img.complete && img.naturalHeight === 0) {
            img.classList.add('missing-image');
        }
    });
} 