const form = document.getElementById("paste-form")

form.addEventListener('submit', async e => {
    e.preventDefault();
    const language = form.language.value;
    const content = form.content.value;
    const response = await fetch('/pastes', {
        method: 'POST',
        headers: {
            'X-Syntax': language
        },
        body: content
    });
    const text = await response.text();
    if (await response.status != 200) {
        document.body.textContent = "Paste data too large. Refresh page to try again";
    } else {
        window.location.href = `${window.location.origin}/${text}`
    }
})