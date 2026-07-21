package com.jobtracker.service;

import com.jobtracker.model.ResumeProfile;
import com.jobtracker.model.User;
import com.jobtracker.repository.ResumeProfileRepository;
import com.jobtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeProfileRepository resumeProfileRepository;
    private final UserRepository userRepository;

    public ResumeProfile uploadResume(Long userId, MultipartFile file) throws IOException {
        String extractedTextFromPdf;
        try(PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            extractedTextFromPdf = stripper.getText(document);
        }

        User user =userRepository.getReferenceById(userId);
        ResumeProfile resumeProfile = resumeProfileRepository.findByUserId(userId)
                .orElse(ResumeProfile.builder().user(user).build());


        resumeProfile.setOriginalFilename(file.getOriginalFilename());
        resumeProfile.setExtractedText(extractedTextFromPdf);
        resumeProfile.setUploadedAt(LocalDateTime.now());

        return resumeProfileRepository.save(resumeProfile);
    }

    public String getResumeText(Long userId){
        return resumeProfileRepository.findByUserId(userId).map(ResumeProfile::getExtractedText).orElseThrow(() -> new IllegalStateException("No resume uploaded yet. One is needed before running a match can be used"));
    }
}
