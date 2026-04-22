import os
import re

ui_dir = 'c:/Users/mzoug/OneDrive/Desktop/Plateforme_E_Learning_FST-dev1 (1)/Plateforme_E_Learning_FST-dev1/src/main/resources/templates/ui'

for root, dirs, files in os.walk(ui_dir):
    for file in files:
        if file == 'list.html':
            filepath = os.path.join(root, file)
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()
            
            # Replace basic structures with modern ones
            content = content.replace('<body class="bg-light">', '<body class="d-flex flex-column min-vh-100 bg-light">')
            content = content.replace('<body>', '<body class="d-flex flex-column min-vh-100 bg-light">')
            
            # Use main flex-grow-1
            if '<div class="container mt-4">' in content:
                content = content.replace('<div class="container mt-4">', '<main class="container my-5 flex-grow-1">')
                content = content.replace('</body>', '</main>\n<footer th:replace="~{layout/fragments :: footer}"></footer>\n</body>')
            
            # Replace h2 with page-header
            content = re.sub(r'<h2>(.*?)</h2>', r'<div class="d-flex justify-content-between align-items-center mb-4"><h2 class="page-title mb-0">\1</h2></div>', content)
            
            # Wrap table in card if not already
            if '<table class="table' in content and '<div class="card shadow-sm border-0">' not in content:
                content = content.replace('<table class="table', '<div class="card shadow-sm border-0"><div class="card-body p-0"><div class="table-responsive"><table class="table table-hover align-middle mb-0"')
                content = content.replace('</table>', '</table></div></div></div>')
                
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(content)
                
        elif file == 'form.html':
            filepath = os.path.join(root, file)
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()
            
            content = content.replace('<body class="bg-light">', '<body class="d-flex flex-column min-vh-100 bg-light">')
            content = content.replace('<body>', '<body class="d-flex flex-column min-vh-100 bg-light">')
            
            if '<div class="container mt-4">' in content:
                content = content.replace('<div class="container mt-4">', '<main class="container my-5 flex-grow-1">')
                content = content.replace('</body>', '</main>\n<footer th:replace="~{layout/fragments :: footer}"></footer>\n</body>')
                
            content = re.sub(r'<h2>(.*?)</h2>', r'<div class="mb-4"><h2 class="page-title">\1</h2></div>', content)
            
            if '<form' in content and '<div class="card shadow-sm border-0">' not in content:
                content = re.sub(r'(<form[^>]*>)', r'<div class="card shadow-sm border-0"><div class="card-body p-4">\1', content)
                content = content.replace('</form>', '</form></div></div>')
                
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(content)
